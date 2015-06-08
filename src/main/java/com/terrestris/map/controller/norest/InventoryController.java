package com.terrestris.map.controller.norest;

import com.terrestris.map.controller.InventoryManager;
import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.InventoryType;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.ItemService;
import com.terrestris.map.service.inter.ProfileService;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dcampbell2 on 4/6/2015.
 */

@Controller
public class InventoryController extends InventoryManager {

    @Autowired
    public InventoryController(InventoryService inventoryService, ProfileService profileService,
                               ItemService itemService, ActivityLogService activityLogService) {
        super(inventoryService, profileService, itemService, activityLogService);

        this.jsFiles = Arrays.asList("inventory.js");
        this.cssFiles = Arrays.asList("inventory.css");
    }

    @RequestMapping(value = "/inventory")
    public String getProfileInventory(Authentication auth, Model model) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        List<ProfileInventory> inventoryList = inventoryService.getAllByPid(profile);
        model.addAttribute("inventory", inventoryService.getAllByPid(profile));
        model.addAttribute("itypes", InventoryType.values());
        model.addAttribute("profile", profile);
        return "views/inventory";
    }

    @RequestMapping(value = "/inventory/create", method = RequestMethod.POST)
    public String getCreateInventory(HttpServletRequest request, Authentication authentication, RedirectAttributes ra) {
        Profile profile = ((CurrentUser)authentication.getPrincipal()).getProfile();
        createInventory(profile);
        return "redirect:/inventory";
    }

    @RequestMapping(value = "/inventory/use", method = RequestMethod.POST)
    public String getUseInventory(HttpServletRequest request, Authentication authentication, RedirectAttributes ra) {
        Profile profile = ((CurrentUser)authentication.getPrincipal()).getProfile();
        String invid = request.getParameter("invid");
        useInventory(profile, Long.parseLong(invid));
        return "redirect:/inventory";
    }

    @RequestMapping(value = "/inventory/remove", method = RequestMethod.POST)
    public String getRemoveInventory(HttpServletRequest request, Authentication auth, RedirectAttributes ra) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        String invid = request.getParameter("invid");
        String count = request.getParameter("count");
        removeInventory(profile, Long.parseLong(invid), Integer.parseInt(count));
        return "redirect:/inventory";
    }

}
