package com.terrestris.map.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by dcampbell2 on 4/6/2015.
 */

@Entity
public class ProfileInventory extends Inventory {

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Profile pid;

    @Transient
    private static Location lid;
    @Transient
    public final String ACTION = "Sell";

    public ProfileInventory() {}
    public ProfileInventory(Profile pid, Item iid, int count) {
        super(iid, count);
        this.pid = pid;
    }

    public Profile getPid() {
        return pid;
    }

    public void setPid(Profile pid) {
        this.pid = pid;
    }

    public static Location getLid() {
        return lid;
    }

    public static void setLid(Location lid) {
        ProfileInventory.lid = lid;
    }

    @Override
    public String getTitle() {
        return pid.getHandle();
    }

    @Override
    public int getSellValue(Profile profile, float lmarkup) {
        if (profile == null) profile = pid;
        float netMarkup = lmarkup - profile.getMarkup();
        return Math.round(iid.getValue() - (iid.getValue() * (netMarkup / (float)100)));
    }

    @Override
    public int getBuyValue(Profile profile, float lmarkup) {
        if (profile == null) profile = pid;
        float netMarkup = lmarkup - profile.getMarkup();
        return Math.round(iid.getValue() + (iid.getValue() * (netMarkup / (float)100)));
    }

    @Override
    public int getPrice() {
        float lmarkup = (lid == null ? (float)1 : lid.getMarkup());
        return getSellValue(pid, lmarkup);
    }

    @Override
    public String toString() {
        return String.format("item: '%s', count: %d, profile: '%s'", iid, count, pid.getHandle());
    }
}
