package com.terrestris.map.controller;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.BountyName;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

/**
 * Created by dcampbell2 on 4/9/2015.
 */
public class BountyManager extends TerrestrisController {

    protected final BountyService bountyService;
    protected final ProfileService profileService;
    protected final RoadService roadService;
    protected final LocationService locationService;
    protected final InventoryService inventoryService;
    protected final ItemService itemService;

    @Autowired
    public BountyManager(BountyService bountyService, ProfileService profileService, RoadService roadService,
                         LocationService locationService, InventoryService inventoryService, ItemService itemService) {
        this.bountyService = bountyService;
        this.profileService = profileService;
        this.roadService = roadService;
        this.locationService = locationService;
        this.inventoryService = inventoryService;
        this.itemService = itemService;
    }

    protected RestBountyData getBounty(Profile profile, long bid) {
        ArrayList<String> errors = new ArrayList<>();
        Optional<Bounty> bountyOpt = bountyService.getBountyByBid(bid);
        if (bountyOpt.isPresent()) {
            Bounty bounty = bountyOpt.get();
            if (profile.isAtBounty(bounty)) {
                return new RestBountyData(bounty, profile);
            } else {
                errors.add("Profile is not at bounty location");
            }
        } else {
            errors.add("Invalid bid provided");
        }
        return new RestBountyData(null, profile, errors);
    }

    protected Collection<Bounty> generateBounties(Profile profile) {
        Optional<Location> locationOpt = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());
        if (locationOpt.isPresent() && locationOpt.get().getLtype() == 3) {
            return bountyService.create(profile, locationOpt.get());
        } else {
            return new ArrayList<>();
        }
    }

    protected void moveBounty(Bounty bounty) {
        Random r = new Random();
        boolean xChange = r.nextBoolean(), yChange = r.nextBoolean(), xDir = r.nextBoolean(), yDir = r.nextBoolean();
        if (xChange) bounty.setXpos(roadService.getNextLongitude(bounty.getXpos(), xDir));
        if (yChange) bounty.setYpos(roadService.getNextLatitude(bounty.getYpos(), yDir));
        if (!xChange && !yChange) {
            bounty.setXpos(roadService.getNextLongitude(bounty.getXpos(), xDir));
            bounty.setYpos(roadService.getNextLatitude(bounty.getYpos(), yDir));
        }
        bounty.setSp(Math.max(bounty.getSp() - bounty.getMvcost(), 0));
    }

    protected Optional<Bounty> claimBounty(long bid, Profile profile) {
        Optional<Bounty> bountyOpt = bountyService.getBountyByBid(bid);
        if (bountyOpt.isPresent()) {
            Bounty bounty =  bountyOpt.get();
            if (bounty.getPid() == null) {
                bounty.setStatus(1);
                bounty.setPid(profile);
                bountyService.save(bounty);
            }
        }
        return bountyOpt;
    }

    protected RestBountyData engageBounty(long bid, int strikeType, Profile profile) {
        Optional<Bounty> bountyOpt = bountyService.getBountyByBid(bid);
        ArrayList<String> errors = new ArrayList<>();
        if (bountyOpt.isPresent()) {
            Bounty bounty = bountyOpt.get();
            if (bounty.getPid().equals(profile) && profile.isAtBounty(bounty)) {
                boolean result = false;
                switch (strikeType) {
                    case 1:
                        result = strike(bounty, profile);
                        break;
                    case 2:
                        result = cast(bounty, profile);
                        break;
                }
                if (!result) moveBounty(bounty);
                bountyService.save(bounty);
                profileService.save(profile);
                return checkMoved(profile, bounty);
            } else {
                errors.add("you have no claim to a bounty at this location");
            }
        } else {
            errors.add("the requested bounty does not exist");
        }
        System.out.println(errors);
        return new RestBountyData(bountyOpt.orElse(null), profile, errors);
    }

    protected boolean cast(Bounty b, Profile p) {
        Random r = new Random();
        int result = r.nextInt(b.getEvasion());
        useProfileMana(p, b.getEvasion());
        useProfileStamina(p, b.getEvasion());
        if (result > b.getEvasion() / 4 && p.getStatValue(Stat.MANA) > b.getEvasion() / 4) {
            b.setStatus(2);
            return true;
        } else {
            return false;
        }
    }

    protected boolean strike(Bounty b, Profile p) {
        Random r = new Random();
        int result = r.nextInt(b.getEvasion() + (b.getEvasion() / 5));
        float buff = (b.spRatio() > 0.95 ? (float)1.2 : (b.spRatio() < 0.50 ? (float)0.8 : 1));
        useProfileStamina(p, b.getEvasion());
        if (b.spRatio() * buff * b.evasionRatio() < p.spRatio() || b.getEvasion() * buff < result) {
            b.setStatus(2);
            return true;
        } else {
            return false;
        }
    }

    protected void useProfileStamina(Profile profile, int targetEv) {
        ProfileStat sp = profile.getStatMap().get(Stat.STAMINA);
        sp.setValue(Math.max(sp.getValue() - 5, 0));
    }

    public void useProfileMana(Profile profile, int targetEv) {
        ProfileStat mp = profile.getStatMap().get(Stat.MANA);
        mp.setValue(Math.max(mp.getValue() - (targetEv / 4), 0));
    }

    protected Optional<Bounty> cashBounty(long bid, Profile profile) {
        Optional<Bounty> bountyOpt = bountyService.getBountyByBid(bid);
        ArrayList<ProfileInventory> rewards = new ArrayList<>();

        if (bountyOpt.isPresent() && bountyOpt.get().getPid().equals(profile)) {
            Bounty bounty = bountyOpt.get();
            if (bounty.getStatus() == 2) {
                int levels = profile.getStat(Stat.EXPERIENCE).addValue(bounty.getXp());
                inventoryService.addItem(profile, itemService.LEVEL_BOON, levels);

                long bountyItemId = (long)BountyName.getMap().get(bounty.getBname()).getIid();
                inventoryService.addItem(profile, bountyItemId, 1);

                profile.setTriste(profile.getTriste() + bounty.getValue());
                profileService.save(profile);

                bounty.setStatus(3);
                bountyService.save(bounty);
            }
        }
        return bountyOpt;
    }

    private RestBountyData checkMoved(Profile profile, Bounty bounty) {
        if (profile.isAtBounty(bounty)) {
            return new RestBountyData(bounty, profile);
        }
        return new RestBountyData(true);
    }

    /*** THE RESULTING OBJECT ***/
    protected class RestBountyData{
        private Bounty bounty;
        private Profile profile;
        private ArrayList<ProfileStat> profileStats;
        private ArrayList<String> errors;
        private boolean isMoved;

        public RestBountyData(boolean isMoved) {
            this.isMoved = isMoved;
        }
        public RestBountyData(Bounty bounty, Profile profile) {
            this.bounty = bounty;
            this.profile = profile;
            this.profileStats = profile.getSortedProfileStats();
            this.isMoved = false;
        }
        public RestBountyData(Bounty bounty, Profile profile, ArrayList<String> errors) {
            this.bounty = bounty;
            this.profile = profile;
            this.profileStats = profile.getSortedProfileStats();
            this.errors = errors;
            this.isMoved = false;
        }

        public Bounty getBounty() {
            return bounty;
        }

        public void setBounty(Bounty bounty) {
            this.bounty = bounty;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public ArrayList<ProfileStat> getProfileStats() {
            return profileStats;
        }

        public void setProfileStats(ArrayList<ProfileStat> profileStats) {
            this.profileStats = profileStats;
        }

        public boolean isMoved() {
            return isMoved;
        }

        public void setIsMoved(boolean isMoved) {
            this.isMoved = isMoved;
        }

        public ArrayList<String> getErrors() {
            return errors;
        }

        public void setErrors(ArrayList<String> errors) {
            this.errors = errors;
        }
    }
}
