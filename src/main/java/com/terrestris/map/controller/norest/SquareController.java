package com.terrestris.map.controller.norest;

import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.service.inter.*;
import com.terrestris.map.service.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by dcampbell2 on 3/30/2015.
 */

@Controller
public class SquareController extends TerrestrisController {

    private final ProfileService profileService;
    private final BountyService bountyService;
    private final LocationService locationService;

    @Autowired
    public SquareController(ProfileService profileService, BountyService bountyService, LocationService locationService) {
        this.profileService = profileService;
        this.bountyService = bountyService;
        this.locationService = locationService;

        this.cssFiles = Arrays.asList("square.css", "shop.css");
        this.jsFiles = Arrays.asList("square.js", "shop.js");
    }

    @RequestMapping(value = "/square")
    public String getSquarePage(Authentication auth, Model model) {
        Profile profile = ((CurrentUser) auth.getPrincipal()).getProfile();

        Page<Profile> userProfiles = profileService.getProfilesByCoords(profile.getXpos(), profile.getYpos());
        Optional<Location> location = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());
        Collection<Bounty> bounties = bountyService.getAllBountiesByPidAndCoords(profile, profile.getXpos(), profile.getYpos());

        model.addAttribute("profile", profile);
        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("totalUsers", userProfiles.getTotalElements());
        model.addAttribute("bounties", bounties);
        model.addAttribute("location", location.orElse(null));
        model.addAttribute("lat", profile.getYpos());
        model.addAttribute("lon", profile.getXpos());

        return "views/square";
    }
}