package com.terrestris.map.controller.norest;

import com.terrestris.map.config.Layout;
import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.object.InventoryType;
import com.terrestris.map.domain.object.Perk;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.*;
import com.terrestris.map.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * Created by dcampbell2 on 3/5/2015.
 */

@Controller
@Layout(value = "layouts/map")
public class MapController extends TerrestrisController {

    private final ProfileService profileService;
    private final RoadService roadService;
    private final ActivityLogService activityLogService;
    private final LocationService locationService;
    private final BountyService bountyService;
    private final InventoryService inventoryService;

    @Autowired
    public MapController(ProfileService profileService, RoadService roadService, ActivityLogService activityLogService,
                         LocationService locationService, BountyService bountyService, InventoryService inventoryService) {
        this.profileService = profileService;
        this.roadService = roadService;
        this.activityLogService = activityLogService;
        this.locationService = locationService;
        this.bountyService = bountyService;
        this.inventoryService = inventoryService;

        this.cssFiles = Arrays.asList("map.css", "profile_stats.css");
        this.jsFiles = Arrays.asList("map.js");
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessProfile(principal, #pid)")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public String getMapPage(@ModelAttribute("pid")long pid, Authentication authentication, RedirectAttributes ra) {
        Profile profile = profileService.getProfileByPid(pid)
                .orElseThrow(() -> new NoSuchElementException(String.format("Profile=%d not found", pid)));
        CurrentUser user = (CurrentUser)authentication.getPrincipal();
        user.setProfile(profile);

        return "redirect:/map";
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String getMapPageRe(Model model, Authentication auth) {
        CurrentUser user = ((CurrentUser) auth.getPrincipal());
        Profile profile = user.getProfile();

        int refill = profile.getStatMap().get(Stat.STAMINA).refillStat(false);
        if (refill > 0) {
            ActivityLog replenished = new ActivityLog(user.getUser(), profile);
            replenished.setAid(Activity.REPLENISHES);
            replenished.setCount(refill);
            activityLogService.create(replenished);
            profileService.save(profile);
        }

        Page<Profile> userProfiles = profileService.getProfilesByCoords(profile.getXpos(), profile.getYpos());
        Optional<Location> location = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());
        Collection<Bounty> bounties = bountyService.getAllBountiesByPidAndCoords(profile, profile.getXpos(), profile.getYpos());
        ArrayList<ProfileInventory> profileInventory = inventoryService.getAllByPid(profile);

        ArrayList<PerkObject> perks = new ArrayList<>();
        for (Perk perkEnum : Perk.values()) {
            PerkObject toAdd = new PerkObject(perkEnum, profile.getPerk(perkEnum));
            if (!toAdd.isHasPerk()) {
                toAdd.setOfferings(inventoryService.getProfilePerks(profileInventory, perkEnum));
                for (PerkInventory offering : toAdd.getOfferings()){
                    if (!offering.isHasItem()) {
                        toAdd.setCanBuy(false);
                        break;
                    }
                }
            }
            perks.add(toAdd);
        }

        model.addAttribute("profile", profile);
        model.addAttribute("totalProfiles", userProfiles.getTotalElements() - 1);
        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("bounties", bounties);
        model.addAttribute("location", location.orElse(null));
        model.addAttribute("inventory", inventoryService.getAllByPid(profile));
        model.addAttribute("itypes", InventoryType.values());
        model.addAttribute("perks", perks);
        model.addAttribute("activityLog", activityLogService.getActivityLogByPid(0, 10, profile));
        model.addAttribute("profileStats", profile.getSortedProfileStats());
        model.addAttribute("lats", roadService.getLatitudesGrid(profile.getYpos()));
        model.addAttribute("lons", roadService.getLongitudesGrid(profile.getXpos()));

        return "views/map";
    }

    public class PerkObject {
        private Perk perk;
        private ProfilePerk profilePerk;
        private List<PerkInventory> offerings;
        private boolean hasPerk;
        private boolean canBuy;

        public PerkObject(Perk perk, ProfilePerk profilePerk) {
            this.perk = perk;
            this.profilePerk = profilePerk;
            this.hasPerk = (profilePerk == null ? false : true);
            this.canBuy = true;
        }

        public Perk getPerk() {
            return perk;
        }

        public void setPerk(Perk perk) {
            this.perk = perk;
        }

        public ProfilePerk getProfilePerk() {
            return profilePerk;
        }

        public void setProfilePerk(ProfilePerk profilePerk) {
            this.profilePerk = profilePerk;
        }

        public List<PerkInventory> getOfferings() {
            return offerings;
        }

        public void setOfferings(List<PerkInventory> offerings) {
            this.offerings = offerings;
        }

        public boolean isHasPerk() {
            return hasPerk;
        }

        public void setHasPerk(boolean hasPerk) {
            this.hasPerk = hasPerk;
        }

        public boolean isCanBuy() {
            return canBuy;
        }

        public void setCanBuy(boolean canBuy) {
            this.canBuy = canBuy;
        }
    }
}
