package com.terrestris.map.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrestris.map.domain.object.Activity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dcampbell2 on 3/24/2015.
 */

@Entity
@Table(name = "activity_log")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alid", nullable = false, updatable = false)
    private long alid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User uid;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Profile pid;

    @ManyToOne
    @JoinColumn(name = "other_pid", referencedColumnName = "pid")
    private Profile otherPid;

    @ManyToOne
    @JoinColumn(name = "xpos", referencedColumnName = "rid")
    private Longitude xpos;

    @ManyToOne
    @JoinColumn(name = "ypos", referencedColumnName = "rid")
    private Latitude ypos;

    @ManyToOne
    @JoinColumn(name = "iid", referencedColumnName = "iid")
    private Item iid;

    @Basic(optional = false)
    @Column(name = "atstamp", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date atstamp;

    private int count;
    private Activity aid;

    public ActivityLog() {};
    public ActivityLog(User uid, Profile pid) {
        this.uid = uid;
        this.pid = pid;
    }
    public ActivityLog(User uid, Profile pid, Latitude ypos, Longitude xpos) {
        this.uid = uid;
        this.pid = pid;
        this.ypos = ypos;
        this.xpos = xpos;
    }
    public ActivityLog(Profile pid, Profile otherPid, Activity activity, Item item, int count) {
        this.uid = pid.getUid();
        this.pid = pid;
        this.otherPid = otherPid;
        this.aid = activity;
        this.iid = item;
        this.ypos = pid.getYpos();
        this.xpos = pid.getXpos();
        this.count = count;
    }
    public long getAlid() {
        return alid;
    }

    /*** HTML Formatting ***/
    private String formatKeyword(String clazz, String content) {
        return String.format("<span class=\"log_text %s\">%s</span>", clazz, content);
    }
    public String getHtml() {
        String result = formatKeyword("handle", pid.getHandle());
        result = result.concat(formatKeyword("activity", aid.getActivity()));
        switch (aid.getActivity()) {
            case "moves":
                result = result.concat(String.format("to %s and %s", xpos.getRname(), ypos.getRname()));
                break;
            case "replenishes":
                result = result.concat(String.format("%d %s", count, "stamina"));
                break;
            case "finds":
            case "buys":
            case "sells":
            case "drops":
            case "uses":
                result = result.concat(String.format("%d %s", count, iid.getItem()));
                break;
            case "attacks":
            case "defeats":
                result = result.concat(String.format("%s", "a bounty"));
                break;
        }
        return result;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }

    public void setPid(Profile pid) {
        this.pid = pid;
    }

    public void setXpos(Longitude xpos) {
        this.xpos = xpos;
    }

    public void setYpos(Latitude ypos) {
        this.ypos = ypos;
    }

    public void setAtstamp(Date atstamp) {
        this.atstamp = atstamp;
    }
    @JsonIgnore
    public User getUid() {
        return uid;
    }
    @JsonIgnore
    public Profile getPid() {
        return pid;
    }

    public Profile getOtherPid() {
        return otherPid;
    }

    public Longitude getXpos() {
        return xpos;
    }

    public Latitude getYpos() {
        return ypos;
    }

    public Activity getAid() {
        return aid;
    }

    public Item getIid() {
        return iid;
    }

    public Date getAtstamp() {
        return atstamp;
    }

    public void setAid(Activity aid) {
        this.aid = aid;
    }

    public void setIid(Item iid) {
        this.iid = iid;
    }

    public void setOtherPid(Profile otherPid) { this.otherPid = otherPid; }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("[\n" +
                "\talid: %d, \n" +
                "\tactivty: '%s', \n" +
                "\tlatitude: '%s', \n" +
                "\tlongitude: '%s', \n" +
                "\ttimestamp: '%s', \n" +
                "\tprofile: '%s', \n]",
                alid, aid, ypos, xpos, atstamp.toString(), pid
        );
    }
}
