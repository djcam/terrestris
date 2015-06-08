package com.terrestris.map.controller.rest;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Perk;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.ProfileService;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by dcampbell2 on 5/14/2015.
 */

@RestController
public class RestPerkController {

    private final InventoryService inventoryService;
    private final ProfileService profileService;
    private final RoadService roadService;

    @Autowired
    public RestPerkController(InventoryService inventoryService, ProfileService profileService,
                              RoadService roadService) {
        this.inventoryService = inventoryService;
        this.profileService = profileService;
        this.roadService = roadService;
    }

    @RequestMapping("/api/perk/buy")
    public Set<ProfilePerk> getPerkBuy(HttpServletRequest request, Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        Perk perk = Perk.valueOf(Integer.parseInt(request.getParameter("perkid")));

        //Not at level requirement
        if (profile.getLevel() < perk.getLevel()) {
            return profile.getProfilePerks();
        }

        ArrayList<PerkInventory> offerings = inventoryService.getAllByPerkid(perk);
        List<Item> iids = new ArrayList<>();
        for (PerkInventory perkinv : offerings) {
            iids.add(perkinv.getIid());
        }
        HashMap<Long, ProfileInventory> pmap = inventoryService.getAllByPidAndIids(profile, iids);
        List<ProfileInventory> updatedInventory = new ArrayList<>();
        for (PerkInventory offering : offerings) {
            ProfileInventory pinv = pmap.get(offering.getIid().getIid());
            if (pinv != null) {
                //Not enough of an item
                if (pinv.getCount() < offering.getCount()) {
                    return profile.getProfilePerks();
                } else {
                    pinv.setCount(pinv.getCount() - offering.getCount());
                    updatedInventory.add(pinv);
                }
            } else {
                //Missing item
                return profile.getProfilePerks();
            }
        }
        inventoryService.saveAll(updatedInventory);
        profile.getProfilePerks().add(new ProfilePerk(profile, perk));
        return profileService.saveProfilePerks(profile.getProfilePerks());
    }

    @RequestMapping("/api/perk/use")
    public Set<ProfilePerk> getPerkUse(HttpServletRequest request, Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        Perk perk = Perk.valueOf(Integer.parseInt(request.getParameter("perkid")));

        ProfilePerk profilePerk = profile.getPerk(perk);
        if (profilePerk == null) {
            //Don't have the perk
            return profile.getProfilePerks();
        }

        profilePerk.setActive(1);
        profilePerk.setLastUse(new Date());
        profileService.saveProfilePerk(profilePerk);

        if (perk == Perk.TELEPORT) {
            Optional<Latitude> lat = roadService.getLatitudeByRid(Long.parseLong(request.getParameter("ypos")));
            Optional<Longitude> lon = roadService.getLongitudeByRid(Long.parseLong(request.getParameter("xpos")));
            profile.setXpos(lon.orElse(profile.getXpos()));
            profile.setYpos(lat.orElse(profile.getYpos()));
            profileService.save(profile);
        }

        return profile.getProfilePerks();
    }
}
