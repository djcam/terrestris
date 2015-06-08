package com.terrestris.map.controller.norest;

import com.terrestris.map.controller.TerrestrisController;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/28/2015.
 */

@Controller
public class ShopController extends TerrestrisController {

    private final LocationService locationService;
    private final InventoryService inventoryService;

    @Autowired
    public ShopController(LocationService locationService, InventoryService inventoryService) {
        this.locationService = locationService;
        this.inventoryService = inventoryService;
        this.cssFiles = Arrays.asList("shop.css");
        this.jsFiles = Arrays.asList("shop.js");
    }

    @RequestMapping(value = "/shop")
    public String getLocationInventory(Authentication auth, Model model, RedirectAttributes ra) {
        Profile profile = ((CurrentUser) auth.getPrincipal()).getProfile();
        Optional<Location> locationOpt = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());
        ArrayList<ArrayList<Inventory>> inventory = new ArrayList<>();

        if (locationOpt.isPresent() && locationOpt.get().getLtype() == 2) {
            Location location = locationOpt.get();

            ArrayList<Inventory> profileInventory = new ArrayList<>(inventoryService.getAllByLid(location));
            ProfileInventory.setLid(location);
            ArrayList<Inventory> locationInventory = new ArrayList<>(inventoryService.getAllByPid(profile));
            LocationInventory.setPid(profile);

            inventory.add(profileInventory);
            inventory.add(locationInventory);

            model.addAttribute("profile", profile);
            model.addAttribute("location", location);
            model.addAttribute("inventoryList", inventory);
            return "views/shop";
        } else {
            return "redirect:/square";
        }
    }
}
