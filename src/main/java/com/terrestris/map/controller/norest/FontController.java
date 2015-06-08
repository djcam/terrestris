package com.terrestris.map.controller.norest;

import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.LocationService;
import com.terrestris.map.service.inter.ProfileService;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by dcampbell2 on 5/4/2015.
 */

@Controller
public class FontController extends TerrestrisController {

    private final RoadService roadService;
    private final LocationService locationService;
    private final ProfileService profileService;

    @Autowired
    public FontController(ProfileService profileService, RoadService roadService,
                          LocationService locationService) {
        this.profileService = profileService;
        this.locationService = locationService;
        this.roadService = roadService;

        this.jsFiles = Arrays.asList("font.js");
        this.cssFiles = Arrays.asList("font.css");
    }

    @RequestMapping(value = "/font")
    public String getFontPage(Model model, Authentication auth, RedirectAttributes ra) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        Optional<Location> locationOpt = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());

        if (locationOpt.isPresent() && profile.isAtLocation(locationOpt.get())) {
            Location location = locationOpt.get();
            boolean active = (profile.getStat(Stat.MANA).isActive());
            Font font = new Font(location.getLname(), location.getLid(), active);
            model.addAttribute("font", font);
            model.addAttribute("mana", profile.getStat(Stat.MANA));
            return "views/font";
        }
        return "redirect:/square";
    }

    public class Font {

        private String fname;
        private long lid;
        private boolean active;

        public Font(String fname, long lid, boolean active) {
            this.fname = fname;
            this.lid = lid;
            this.active = active;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public long getLid() {
            return lid;
        }

        public void setLid(long lid) {
            this.lid = lid;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}
