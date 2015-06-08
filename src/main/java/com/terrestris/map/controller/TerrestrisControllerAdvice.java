package com.terrestris.map.controller;

import com.terrestris.map.service.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by dcampbell2 on 2/28/2015.
 */

@ControllerAdvice
public class TerrestrisControllerAdvice {

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }

    @ModelAttribute("mainTitle")
    public String getMainTitle () {
        return "Terrestris";
    }
}
