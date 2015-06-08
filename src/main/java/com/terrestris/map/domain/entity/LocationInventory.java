package com.terrestris.map.domain.entity;

import javax.persistence.*;

/**
 * Created by dcampbell2 on 4/10/2015.
 */

@Entity
public class LocationInventory extends Inventory {

    @ManyToOne
    @JoinColumn(name = "lid", referencedColumnName = "lid")
    private Location lid;

    @Transient
    private static Profile pid;
    @Transient
    public final String ACTION = "Buy";

    public LocationInventory() {}

    public LocationInventory(Location lid, Item iid, int count) {
        super(iid, count);
        this.lid = lid;
    }

    public Location getLid() {
        return lid;
    }

    public void setLid(Location lid) {
        this.lid = lid;
    }

    @Override
    public String getTitle() {
        return lid.getLname();
    }

    public static Profile getPid() {
        return pid;
    }

    public static void setPid(Profile pid) {
        LocationInventory.pid = pid;
    }

    @Override
    public int getSellValue(Profile profile, float lmarkup) {
        float netMarkup = lmarkup - profile.getMarkup();
        return Math.round(iid.getValue() + (iid.getValue() * (netMarkup / (float)100)));
    }

    @Override
    public int getBuyValue(Profile profile, float lmarkup) {
        float netMarkup = lmarkup - profile.getMarkup();
        return Math.round(iid.getValue() - (iid.getValue() * (netMarkup / (float)100)));
    }

    @Override
    public int getPrice() {
        float lmarkup = (lid == null ? (float)1 : lid.getMarkup());
        return getSellValue(pid, lmarkup);
    }

    @Override
    public String toString() {
        return String.format("item: '%s', count: %d, location: '%s'", iid, count, lid.getLname());
    }
}
