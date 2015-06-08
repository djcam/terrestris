package com.terrestris.map.domain.entity;

import javax.persistence.*;

/**
 * Created by dcampbell2 on 4/14/2015.
 */

@MappedSuperclass
abstract public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long invid;

    @ManyToOne
    @JoinColumn(name = "iid", referencedColumnName = "iid")
    protected Item iid;

    protected int count;

    public Inventory() {}
    public Inventory(Item iid, int count) {
        this.iid = iid;
        this.count = count;
    }

    public long getInvid() {
        return invid;
    }

    public Item getIid() {
        return iid;
    }

    public void setIid(Item iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int add(int toAdd) {
        int diff = Math.min(count + toAdd, iid.getMaxcount()) - count;
        count = diff + count;
        return diff;
    }

    public void remove(int toRemove) {
        count = Math.max(count - toRemove, 0);
    }

    public abstract String getTitle();
    public abstract int getSellValue(Profile profile, float lmarkup);
    public abstract int getBuyValue(Profile profile, float lmarkup);
    public abstract int getPrice();
}
