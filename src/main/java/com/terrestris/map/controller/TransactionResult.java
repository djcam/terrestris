package com.terrestris.map.controller;

import com.terrestris.map.domain.entity.*;

import java.util.List;

/**
 * Created by dcampbell2 on 5/4/2015.
 */
public class TransactionResult {
    private List<ProfileInventory> inventory;
    private List<Item> removed;
    private List<ActivityLog> logs;
    private String error;
    private Location location;
    private Profile profile;

    public TransactionResult(String error) {
        this.error = error;
    }
    public TransactionResult(List<ProfileInventory> inventory, List<Item> removed, Location location,
                             Profile profile, List<ActivityLog> logs) {
        this.inventory = inventory;
        this.removed = removed;
        this.location = location;
        this.profile = profile;
        this.logs = logs;
    }

    public List<ProfileInventory> getInventory() {
        return inventory;
    }

    public List<Item> getRemoved() { return removed; }

    public String getError() {
        return error;
    }

    public Location getLocation() {
        return location;
    }

    public Profile getProfile() {
        return profile;
    }

    public List<ActivityLog> getLogs() {
        return logs;
    }

    public boolean getValid() {
        return (error == null ? true : false);
    }
}