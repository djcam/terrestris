package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.InventoryType;
import com.terrestris.map.domain.object.Stat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Created by dcampbell2 on 3/24/2015.
 */

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long iid;

    private int value;
    private int drop;
    private String item;
    private int maxcount;
    private InventoryType itype;
    private Stat stid;
    private int potency;

    public Item() {}

    public Item(Item item) {
        this.iid = item.getIid();
        this.value = (int)item.getValue();
        this.item = item.getItem();
        this.maxcount = item.getMaxcount();
        this.itype = item.getItype();
    }

    public long getIid() {
        return iid;
    }

    public void setIid(long iid) {
        this.iid = iid;
    }

    public int getDrop() {
        return drop;
    }

    public void setDrop(int drop) {
        this.drop = drop;
    }

    public float getValue() {
        return (float)value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public InventoryType getItype() {
        return itype;
    }

    public void setItype(InventoryType itype) {
        this.itype = itype;
    }

    public int getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(int maxcount) {
        this.maxcount = maxcount;
    }

    public Stat getStid() {
        return stid;
    }

    public void setStid(Stat stid) {
        this.stid = stid;
    }

    public int getPotency() {
        return potency;
    }

    public void setPotency(int potency) {
        this.potency = potency;
    }

    @Override
    public String toString() {
        return String.format("iid: %d, item: '%s', value: %d", iid, item, value);
    }
}
