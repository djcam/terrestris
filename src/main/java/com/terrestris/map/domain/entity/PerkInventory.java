package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.Perk;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Created by dcampbell2 on 5/18/2015.
 */

@Entity
public class PerkInventory extends Inventory {

    private Perk perkid;

    @Transient
    boolean hasItem;
    @Transient
    int profileCount;

    public PerkInventory() {}
    public PerkInventory(Perk perkid, Item iid, int count) {
        super(iid, count);
        this.perkid = perkid;
        this.hasItem = false;
        this.profileCount = 0;
    }

    @Override
    public String getTitle() {
        return iid.getItem();
    }

    @Override
    public int getSellValue(Profile profile, float lmarkup) {
        return getPrice();
    }
    @Override
    public int getBuyValue(Profile profile, float lmarkup) {
        return getPrice();
    }

    @Override
    public int getPrice() {
        return (int) iid.getValue() * count;
    }

    public boolean isHasItem() {
        return hasItem;
    }

    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public int getProfileCount() {
        return profileCount;
    }

    public void setProfileCount(int profileCount) {
        this.profileCount = profileCount;
    }
}
