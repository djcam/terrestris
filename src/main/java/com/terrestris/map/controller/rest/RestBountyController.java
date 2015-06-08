package com.terrestris.map.controller.rest;

import com.terrestris.map.controller.BountyManager;
import com.terrestris.map.domain.entity.Bounty;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/9/2015.
 */

@RestController
public class RestBountyController extends BountyManager{

    @Autowired
    public RestBountyController(BountyService bountyService, ProfileService profileService,
                                RoadService roadService, LocationService locationService,
                                InventoryService inventoryService, ItemService itemService) {
        super(bountyService, profileService, roadService, locationService, inventoryService, itemService);
    }


    @RequestMapping(value = "/api/bounty")
    public RestBountyData getBounty(@RequestParam("bid")long bid, Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        return getBounty(profile, bid);
    }

    @RequestMapping("/api/bounty/claim")
    public RestBountyData getClaimBounty(@RequestParam(value = "bid") long bid, Authentication auth) {
        Profile profile = ((CurrentUser) auth.getPrincipal()).getProfile();
        Optional<Bounty> bounty = claimBounty(bid, profile);
        return new RestBountyData(bounty.orElse(null), profile);
    }

    @RequestMapping("/api/bounty/engage")
    public RestBountyData getStrikeBounty(@RequestParam(value = "bid") long bid,
                                          @RequestParam(value = "etype") int etype,
                                          Authentication auth) {
        Profile profile = ((CurrentUser) auth.getPrincipal()).getProfile();
        return engageBounty(bid, etype, profile);
    }

    @RequestMapping("/api/bounty/create")
    public Collection<Bounty> getCreateBounties(Authentication auth, RedirectAttributes ra) {
        return generateBounties(((CurrentUser)auth.getPrincipal()).getProfile());
    }

    @RequestMapping("/api/bounty/cash")
    public RestBountyData getCashBounty(@RequestParam(value = "bid") long bid, Authentication auth) {
        Profile profile = ((CurrentUser) auth.getPrincipal()).getProfile();
        Optional<Bounty> bounty = cashBounty(bid, profile);
        return new RestBountyData(bounty.orElse(null), profile);
    }

}
