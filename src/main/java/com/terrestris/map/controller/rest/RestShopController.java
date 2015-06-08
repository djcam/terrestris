package com.terrestris.map.controller.rest;

import com.terrestris.map.controller.TransactionResult;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.LocationService;
import com.terrestris.map.service.inter.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by dcampbell2 on 4/28/2015.
 */

@RestController
public class RestShopController {
    private final ActivityLogService activityLogService;
    private final LocationService locationService;
    private final ProfileService profileService;
    private final InventoryService inventoryService;

    @Autowired
    public RestShopController(ActivityLogService activityLogService, LocationService locationService,
                                ProfileService profileService, InventoryService inventoryService) {
        this.activityLogService = activityLogService;
        this.locationService = locationService;
        this.profileService = profileService;
        this.inventoryService = inventoryService;
    }

    @RequestMapping("/api/shop")
    public TransactionResult transaction(Authentication auth, HttpServletRequest request) {
        ArrayList<ActivityLog> logs = new ArrayList<>();
        LocationInventory tmp = new LocationInventory();
        Set<Long> invids = new HashSet<>();
        boolean buy = (request.getParameter("type").equals(tmp.ACTION) ? true : false);
        for (String name : request.getParameterMap().keySet()) {
            if (!name.equals("type")) invids.add(Long.parseLong(name));
        }
        List<Inventory> tInventories = new LinkedList<>();
        if (buy) tInventories.addAll(inventoryService.getLAllByInvids(invids));
        else tInventories.addAll(inventoryService.getPAllByInvids(invids));
        if (tInventories.size() != invids.size()) {
            return new TransactionResult("not the same amount of stuff");
        }

        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        HashMap<Item, Integer> items = new HashMap<>();
        Location location = getTransactionLocation(items, tInventories, request, profile, buy);
        if (location == null) return new TransactionResult("bad location");
        if (buy && !profile.isAtLocation(location)) {
            return new TransactionResult("user is not on this square");
        }

        List<Item> removedInventory = new LinkedList<>();
        List<ProfileInventory> updatedInventory = new LinkedList<ProfileInventory>();
        int cost = 0;
        for (Item item : items.keySet()) {
            ProfileInventory profileInventory = inventoryService.getOneByPidAndIid(profile, item)
                    .orElse(new ProfileInventory(profile, item, 0));
            int count = items.get(item);

            if (buy) {
                count = profileInventory.add(count);
                if (count < 1) return new TransactionResult("you have the max amount of this item");
            } else {
                count = Math.min(count, profileInventory.getCount());
                profileInventory.remove(count);
            }

            Activity activity = (buy ? Activity.BUYS : Activity.SELLS);
            logs.add(new ActivityLog(profile, null, activity, profileInventory.getIid(), count));
            if ((buy && count > 0) || (!buy && count > 0 && profileInventory.getCount() > 0)) {
                updatedInventory.add(profileInventory);
            } else if (!buy && profileInventory.getCount() == 0) {
                removedInventory.add(profileInventory.getIid());
                inventoryService.delete(profileInventory);
            }

            if (buy) cost += profileInventory.getBuyValue(null, location.getMarkup()) * count;
            else cost += profileInventory.getSellValue(null, location.getMarkup()) * count;
        }

        if (buy) {
            if (cost > profile.getTriste()) {
                return new TransactionResult("not enough triste");
            } else {
                profile.setTriste(profile.getTriste() - cost);
            }
        } else {
            profile.setTriste(profile.getTriste() + cost);
        }

        ArrayList<ActivityLog> savedLogs = new ArrayList<>();
        savedLogs.addAll(activityLogService.createAll(logs));
        return new TransactionResult(inventoryService.saveAll(updatedInventory), removedInventory, location,
                profileService.save(profile), savedLogs);
    }

    private Location getTransactionLocation(HashMap<Item, Integer> items, List<Inventory> locationInventories,
                                            HttpServletRequest request, Profile profile, boolean buy) {
        long lid, invid, lastLid = -1;
        for (Inventory locationInventory : locationInventories) {
            if (buy) {
                lid = ((LocationInventory) locationInventory).getLid().getLid();
                if (lastLid != -1 && lastLid != lid) return null;
                else lastLid = lid;
            }
            invid = locationInventory.getInvid();
            items.put(locationInventory.getIid(), Integer.parseInt(request.getParameter(Long.toString(invid))));
        }
        if (buy) return locationService.getLocationByLid(lastLid).orElse(null);
        return locationService.getLocationByCoords(profile.getXpos(), profile.getYpos()).orElse(null);
    }

    private boolean consistentProfile() {
        return true;
    }


}
