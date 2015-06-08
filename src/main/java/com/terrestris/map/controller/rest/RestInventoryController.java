package com.terrestris.map.controller.rest;

import com.terrestris.map.controller.InventoryManager;
import com.terrestris.map.controller.TransactionResult;
import com.terrestris.map.domain.entity.Inventory;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.ProfileInventory;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.ItemService;
import com.terrestris.map.service.inter.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dcampbell2 on 5/4/2015.
 */

@RestController
public class RestInventoryController extends InventoryManager {

    @Autowired
    public RestInventoryController(InventoryService inventoryService, ProfileService profileService,
                                   ItemService itemService, ActivityLogService activityLogService) {
        super(inventoryService, profileService, itemService, activityLogService);
    }

    @RequestMapping(value = "/api/inventory")
    public LinkedList<ProfileInventory> getInventory(Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        LinkedList<ProfileInventory> inventory = new LinkedList<>();
        inventory.addAll(inventoryService.getAllByPid(profile));
        return inventory;
    }

    @RequestMapping(value = "/api/inventory/use")
    public TransactionResult getUseInventory(Authentication auth, HttpServletRequest request) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        String invid = request.getParameter("invid");
        return useInventory(profile, Long.parseLong(invid));
    }

    @RequestMapping(value = "/api/inventory/remove")
    public TransactionResult getRemoveInventory(Authentication auth, HttpServletRequest request) {
        //List<ProfileInventory> inventory, List<Item> removed, Location location, Profile profile
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        String invid = request.getParameter("invid");
        String count = request.getParameter("count");
        return removeInventory(profile, Long.parseLong(invid), Integer.parseInt(count));
    }
}
