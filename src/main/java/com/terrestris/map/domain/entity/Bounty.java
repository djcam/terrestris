package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.Stat;

import javax.persistence.*;
import java.util.Random;

/**
 * Created by dcampbell2 on 4/1/2015.
 */

@Entity
public class Bounty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bid;

    @ManyToOne
    @JoinColumn(name = "pid_owner", referencedColumnName = "pid")
    private Profile pid;

    @ManyToOne
    @JoinColumn(name = "lid", referencedColumnName = "lid")
    private Location lid;

    @ManyToOne
    @JoinColumn(name = "ypos", referencedColumnName = "rid")
    private Latitude ypos;

    @ManyToOne
    @JoinColumn(name = "xpos", referencedColumnName = "rid")
    private Longitude xpos;

    private String bname;
    private int value;
    private int sp;
    private int maxsp;
    private int mvcost;
    private int evasion;
    private int status;
    private int xp;

    public Bounty() {
        this.status = 0;
        this.sp = 100;
        this.maxsp = 100;
    }

    public long getBid() {
        return bid;
    }

    public Profile getPid() {
        return pid;
    }

    public void setPid(Profile pid) {
        this.status = 1;
        this.pid = pid;
    }

    public Latitude getYpos() {
        return ypos;
    }

    public void setYpos(Latitude ypos) {
        this.ypos = ypos;
    }

    public Longitude getXpos() {
        return xpos;
    }

    public void setXpos(Longitude xpos) {
        this.xpos = xpos;
    }

    public Location getLid() {
        return lid;
    }

    public void setLid(Location lid) {
        this.lid = lid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getMaxsp() { return maxsp; }

    public void setMaxsp(int maxsp) { this.maxsp = maxsp; }

    public int getMvcost() {
        return mvcost;
    }

    public void setMvcost(int mvcost) {
        this.mvcost = mvcost;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) { this.xp = xp; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public float spRatio() {
        return (float)sp / (float)maxsp;
    }

    public float evasionRatio() {
        return (float)evasion / (float)100;
    }

    @Override
    public String toString() {
        return String.format("Bounty: '%s', Lat: '%s', Lon: '%s', Owner: '%s'", bname, getYpos(), getXpos(), pid);
    }
}
