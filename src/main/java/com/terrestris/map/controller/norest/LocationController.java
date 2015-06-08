package com.terrestris.map.controller.norest;

import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.repository.LocationRepository;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Created by dcampbell2 on 4/14/2015.
 */

@Controller
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/locations")
    public String getLocationsPage(Authentication auth, Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "views/location";
    }

    @RequestMapping(value = "/location")
    public String redirectLocationPage(@ModelAttribute("lid")long lid, Authentication auth, Model model,
                                       RedirectAttributes ra) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        Optional<Location> locationOpt = locationService.getLocationByLid(lid);
        String direct = ":redirect/square";
        if (locationOpt.isPresent() && profile.isAtLocation(locationOpt.get())) {
            switch(locationOpt.get().getLtype()) {
                case 1:
                    direct = "redirect:/font";
                    break;
                case 2:
                    direct = "redirect:/shop";
                    break;
                case 3:
                    direct = "redirect:/bounties";
                    break;
                default:
                    break;
            }
        }
        return direct;
    }
}
