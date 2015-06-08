package com.terrestris.map.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrestris.map.domain.object.Stat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dcampbell2 on 3/24/2015.
 */
@Entity
@Table(name = "profile_stat")
public class ProfileStat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pstid;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Profile pid;

    @Basic(optional = false)
    @Column(name = "refill")
    @Temporal(TemporalType.TIMESTAMP)
    private Date refill;

    private Stat stid;
    private int value;
    private int max;
    private int total;
    private int rate;

    public ProfileStat() {}
    public ProfileStat(int value, int max, int total, int rate, Profile pid, Stat stid) {
        this.pid = pid;
        this.value = value;
        this.max = max;
        this.total = total;
        this.rate = rate;
        this.stid = stid;
    }

    public long getPstid() {
        return pstid;
    }

    @JsonIgnore
    public Profile getPid() {
        return pid;
    }

    public void setPid(Profile pid) {
        this.pid = pid;
    }

    public Stat getStid() {
        return stid;
    }

    public void setStid(Stat stid) {
        this.stid = stid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getRefill() {
        return refill;
    }

    public void setRefill(Date refill) {
        this.refill = refill;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getRatio() {
        float v = (max == 0 ? 0 : ((float)value/(float)max)*(float)100);
        return String.format("%.4g", v).concat("%");
    }

    public boolean isActive() {
        Date d = new Date();
        long diff = d.getTime() - refill.getTime();
        int newVal = ((int) diff / 1000) / rate;
        if (newVal < 1) {
            return false;
        }
        return true;
    }

    public int addValue(int add) {
        if (stid.getStid() == Stat.EXPERIENCE.getStid()) {
            total += add;
            value += add;

            int diff = value - max, tmp = pid.getLevel();
            while (diff >= 0) {
                pid.setLevel(pid.getLevel() + 1);
                value = diff;
                max = Math.max(max + (max / 2), max + 5000);
                diff = value - max;
            }
            System.out.println(pid.getLevel() - tmp);
            return pid.getLevel() - tmp;
        } else {
            this.value = Math.min(max, value + add);
        }
        return 0;
    }

    public int refillStat(boolean full) {
        Date d = new Date();
        long diff = d.getTime() - refill.getTime();
        int newSp = ((int) diff / 1000) / rate;
        int tmp = value;
        if (full) {
            value = max;
        } else {
            value = Math.min(value + newSp, max);
        }
        if (value - tmp > 0) {
            refill = d;
        }
        return value - tmp;
    }

    @Override
    public String toString() {
        return String.format("Stat: '%s', Value: %d, Max: %d, Ratio: '%s'",
                stid.getTitle(), value, max, getRatio()
        );
    }
}
