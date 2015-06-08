package com.terrestris.map.controller.norest;

import com.terrestris.map.controller.BountyManager;
import com.terrestris.map.domain.entity.Bounty;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * Created by dcampbell2 on 4/2/2015.
 */

@Controller
public class BountyController extends BountyManager {

    @Autowired
    public BountyController(BountyService bountyService, ProfileService profileService,
                            RoadService roadService, LocationService locationService,
                            InventoryService inventoryService, ItemService itemService) {
        super(bountyService, profileService, roadService, locationService, inventoryService, itemService);
        this.jsFiles = Arrays.asList("bounty.js");
        this.cssFiles = Arrays.asList("bounty.css", "profile_stats.css");
    }

    @RequestMapping(value = "/bounties")
    public String getBountiesPage(Model model, Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        Optional<Location> locationOpt = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());

        if (locationOpt.isPresent() && locationOpt.get().getLtype() == 3) {
            Location location = locationOpt.get();
            if (location.getRpgcid().getRpgcid() == profile.getRpgcid().getRpgcid()) {
                Collection<Bounty> bounties = bountyService.getAllBountiesByLidAndPid(location, profile);
                int newBounties = Math.max(bountyService.MAX_BOUNTIES - bounties.size(), 0);
                for (Bounty b : bounties) {
                    if (b.getStatus() > 2) newBounties++;
                }
                model.addAttribute("location", location);
                model.addAttribute("bounties", bounties);
                model.addAttribute("profile", ((CurrentUser) auth.getPrincipal()).getProfile());
                model.addAttribute("newBounties", newBounties);
                return "views/bounties";
            }
        }

        return "redirect:/square";
    }

    @RequestMapping(value = "/bounty")
    public String getBountyPage(@ModelAttribute("bid")long bid, Model model, Authentication auth, RedirectAttributes ra) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        RestBountyData rbd = getBounty(profile, bid);
        if (rbd.getBounty() != null) {
                model.addAttribute("profile", profile);
                model.addAttribute("bounty", rbd.getBounty());
                model.addAttribute("profileStats", profile.getSortedProfileStats());
                return "views/bounty";
        }
        return "redirect:/square";
    }

    @RequestMapping(value = "/bounty/create", method = RequestMethod.POST)
    public String createBounty(Authentication auth, RedirectAttributes ra) {
        generateBounties(((CurrentUser)auth.getPrincipal()).getProfile());
        return "redirect:/bounties";
    }

    @RequestMapping(value = "/bounty/claim", method = RequestMethod.POST)
    public String claimBounty(@ModelAttribute("bid")long bid, Authentication auth, RedirectAttributes ra) {
        claimBounty(bid, ((CurrentUser)auth.getPrincipal()).getProfile());
        return "redirect:/bounties";
    }

    @RequestMapping(value = "/bounty/engage", method = RequestMethod.POST)
    public String strikeBounty(@ModelAttribute("bid") long bid, @ModelAttribute("etype") int etype,
                               Authentication auth, RedirectAttributes ra) {
        engageBounty(bid, etype, ((CurrentUser) auth.getPrincipal()).getProfile());
        return "redirect:/bounties";
    }

    @RequestMapping(value = "/bounty/cash", method = RequestMethod.POST)
    public String cashBounty(@ModelAttribute("bid")long bid, Authentication auth, RedirectAttributes ra) {
        cashBounty(bid, ((CurrentUser)auth.getPrincipal()).getProfile());
        return "redirect:/bounties";
    }
}
