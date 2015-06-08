package com.terrestris.map.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrestris.map.domain.object.Perk;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by dcampbell2 on 5/13/2015.
 */

@Entity
@Table(name = "perk")
public class ProfilePerk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pkid;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Profile pid;

    @Basic(optional = false)
    @Column(name = "last_use")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUse;

    private int rate;
    private int active;
    private Perk perkid;

    public ProfilePerk() {}
    public ProfilePerk(Profile pid, Perk perkid) {
        this.pid = pid;
        this.perkid = perkid;
        this.active = 0;
    }

    @JsonIgnore
    public Profile getPid() {
        return pid;
    }

    public void setPid(Profile pid) {
        this.pid = pid;
    }

    public Date getLastUse() {
        return lastUse;
    }

    public void setLastUse(Date lastUse) {
        this.lastUse = lastUse;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Perk getPerkid() {
        return perkid;
    }

    public void setPerkid(Perk perkid) {
        this.perkid = perkid;
    }

    public int getRecharged() {
        if (lastUse == null) return 0;

        Date d = new Date();
        long diff = d.getTime() - lastUse.getTime();
        return rate - (int) (diff / 1000);
    }

    public boolean isRecharged() {
        return ((getRecharged() <= 0) ? true : false);
    }

}
