package com.terrestris.map.controller.norest;


import com.terrestris.map.config.Layout;
import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.object.RpgClass;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.form.ProfileForm;
import com.terrestris.map.form.ProfileFormValidator;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ProfileService;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Controller
@Layout(value = "layouts/base")
public class ProfileController extends TerrestrisController {

    private final ProfileService profileService;
    private final ProfileFormValidator profileFormValidator;
    private final RoadService roadService;
    private final ActivityLogService activityLogService;

    @Autowired
    public ProfileController(ProfileService profileService, ProfileFormValidator profileFormValidator,
                             RoadService roadService, ActivityLogService activityLogService) {
        this.profileService = profileService;
        this.profileFormValidator = profileFormValidator;
        this.roadService = roadService;
        this.activityLogService = activityLogService;

        this.cssFiles = Arrays.asList("profile.css");
        this.jsFiles = Arrays.asList("profile.js");
        this.subTitle = "Profile";
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(profileFormValidator);
    }

    @ModelAttribute("rpgClasses")
    public Collection<RpgClass> populateRpgClasses() {
        Collection<RpgClass> rpgClasses = new ArrayList<>();
        for (RpgClass rpgClass : RpgClass.values()) {
            rpgClasses.add(rpgClass);
        }
        return rpgClasses;
    }

    @RequestMapping("/profile")
    public ModelAndView getProfilePage(Authentication authentication) {
        CurrentUser user = (CurrentUser)authentication.getPrincipal();
        Collection<Profile> profiles = profileService.getProfilesByUid(user.getUser());

        for (Profile profile : profiles) {
            int refill = profile.getStatMap().get(Stat.STAMINA).refillStat(false);
            if (refill > 0) {
                ActivityLog replenished = new ActivityLog(user.getUser(), profile);
                replenished.setAid(Activity.REPLENISHES);
                replenished.setCount(refill);
                activityLogService.create(replenished);
                profileService.save(profile);
            }
        }

        int offset = profiles.size();
        for (int i = 0; i < 3 - offset; i++) {
            profiles.add(new Profile());
        }
        return new ModelAndView("views/profile", "profiles", profiles);
    }

    @RequestMapping(value = "/profile/create", method = RequestMethod.GET)
    public ModelAndView getProfileCreatePage() {
        ModelAndView mv = new ModelAndView("views/profile_create", "form", new ProfileForm());
        return mv;
    }

    @RequestMapping(value = "/profile/create", method = RequestMethod.POST)
    public String handleProfileForm(@Valid @ModelAttribute("form") ProfileForm form, BindingResult bindingResult,
                                    Authentication authentication) {
        CurrentUser user = (CurrentUser)authentication.getPrincipal();

        if (bindingResult.hasErrors()) {
            return "profile_create";
        }
        try {
            profileService.create(form, user.getUser(),
                    roadService.getRandomLongitude(), roadService.getRandomLatitude());
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("handle.exists", "Handle already exists");
            return "views/profile_create";
        }
        return "redirect:/profile";
    }
}
