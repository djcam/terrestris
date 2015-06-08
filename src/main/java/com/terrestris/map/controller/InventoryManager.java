package com.terrestris.map.controller;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.inter.InventoryService;
import com.terrestris.map.service.inter.ItemService;
import com.terrestris.map.service.inter.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by dcampbell2 on 5/4/2015.
 */
public class InventoryManager extends TerrestrisController {

    protected final InventoryService inventoryService;
    protected final ProfileService profileService;
    protected final ActivityLogService activityLogService;
    protected final ItemService itemService;

    @Autowired
    public InventoryManager(InventoryService inventoryService, ProfileService profileService,
                            ItemService itemService, ActivityLogService activityLogService) {
        this.inventoryService = inventoryService;
        this.profileService = profileService;
        this.activityLogService = activityLogService;
        this.itemService = itemService;
    }

    protected void createInventory(Profile profile) {
        ArrayList<Item> items = itemService.getAll();
        Random r = new Random();
        int index = r.nextInt(items.size());
        ProfileInventory inventory = inventoryService.getOneByPidAndIid(profile, items.get(index))
                .orElse(new ProfileInventory(profile, items.get(index), 0));
        inventory.add(1);
        inventoryService.save(inventory);
    }

    protected TransactionResult useInventory(Profile profile, long invid) {
        List<ProfileInventory> updatedInventory = new ArrayList<>();
        List<Item> removedItems = new ArrayList<>();
        List<ActivityLog> logs = new ArrayList<>();

        try {
            ProfileInventory inventory = inventoryService.getPOneByInvid(invid);
            if (inventory.getCount() < 1) {
                return new TransactionResult("Item count is less than 1");
            }

            ProfileStat pstat = profile.getStat(inventory.getIid().getStid());
            int levels = pstat.addValue(inventory.getIid().getPotency());
            inventoryService.addItem(profile, itemService.LEVEL_BOON, levels);
            inventory.remove(1);
            logs.add(activityLogService.create(profile, null, Activity.USES, inventory.getIid(), 1));

            if (inventory.getCount() < 1) {
                removedItems.add(inventory.getIid());
                inventoryService.delete(inventory);
            } else {
                updatedInventory.add(inventory);
                inventoryService.save(inventory);
            }
            profileService.save(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return new TransactionResult("Profile Inventory object does not exist");
        }

        return new TransactionResult(updatedInventory, removedItems, null, profile, logs);
    }

    protected TransactionResult removeInventory(Profile profile, long invid, int count) {
        List<ActivityLog> logs = new ArrayList<>();
        try {
            ProfileInventory inventory = inventoryService.getPOneByInvid(invid);
            if (inventory.getPid().equals(profile)) {
                ArrayList<ProfileInventory> updatedInventory = new ArrayList<>();
                ArrayList<Item> removedItems = new ArrayList<>();

                inventory.remove(count);
                logs.add(activityLogService.create(profile, null, Activity.DROPS, inventory.getIid(), count));
                if (inventory.getCount() < 1) {
                    removedItems.add(inventory.getIid());
                    inventoryService.delete(inventory);
                } else {
                    updatedInventory.add(inventoryService.save(inventory));
                }
                return new TransactionResult(updatedInventory, removedItems, null, profile, logs);
            } else {
                return new TransactionResult("not auth user inventory");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TransactionResult("inventory does not exist");
    }

}
