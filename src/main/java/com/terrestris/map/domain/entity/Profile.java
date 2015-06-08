package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.Perk;
import com.terrestris.map.domain.object.RpgClass;
import com.terrestris.map.domain.object.Stat;

import javax.persistence.*;
import java.util.*;

/**
 * Created by dcampbell2 on 3/4/2015.
 */

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long pid;
    private String handle;
    private int markup;
    private int level;
    private long triste;
    private int mvcost;
    private RpgClass rpgcid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User uid;

    @ManyToOne
    @JoinColumn(name = "xpos", referencedColumnName = "rid")
    private Longitude xpos;

    @ManyToOne
    @JoinColumn(name = "ypos", referencedColumnName = "rid")
    private Latitude ypos;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pid")
    private Set<ProfileStat> profileStats = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pid")
    private Set<ProfilePerk> profilePerks = new HashSet<>();

    @Transient
    private HashMap<Stat, ProfileStat> statMap = new HashMap<>();

    public Profile() {}

    /*** GETTERS & SETTERS ***/
    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public String getHandle() {
        return handle;
    }
    public void setHandle(String handle) {
        this.handle = handle;
    }

    public RpgClass getRpgcid() {
        return rpgcid;
    }
    public void setRpgcid(RpgClass rpgcid) {
        this.rpgcid = rpgcid;
    }

    public User getUid() {
        return uid;
    }
    public void setUid(User uid) {
        this.uid = uid;
    }

    public Longitude getXpos() {
        return xpos;
    }
    public void setXpos(Longitude xpos) {
        this.xpos = xpos;
    }

    public Latitude getYpos() {
        return ypos;
    }
    public void setYpos(Latitude ypos) { this.ypos = ypos; }

    public float getMarkup() { return (float)markup; }
    public void setMarkup(int markup) { this.markup = markup; }

    public long getTriste() { return triste; }
    public void setTriste(long triste) { this.triste = triste; }

    public int getMvcost() {
        return mvcost;
    }
    public void setMvcost(int mvcost) {
        this.mvcost = mvcost;
    }

    public Set<ProfileStat> getProfileStats() {
        return profileStats;
    }
    public void setProfileStats(Set<ProfileStat> profileStats) {
        this.profileStats = profileStats;
    }

    public Set<ProfilePerk> getProfilePerks() { return profilePerks; }
    public void setProfilePerks(Set<ProfilePerk> profilePerks) { this.profilePerks = profilePerks; }

    public ProfilePerk getPerk(Perk perk) {
        for (ProfilePerk profilePerk : profilePerks) {
            if (profilePerk.getPerkid().getPerkid() == perk.getPerkid()) {
                return profilePerk;
            }
        }
        return null;
    }

    public void setStatMap() {
        for (ProfileStat stat : profileStats) {
            statMap.put(stat.getStid(), stat);
        }
    }

    public HashMap<Stat, ProfileStat> getStatMap() {
        if (statMap.size() < profileStats.size()) setStatMap();
        return statMap;
    }

    public ProfileStat getStat(Stat stat) {
        return getStatMap().get(stat);
    }

    public int getStatValue(Stat stat) {
        return getStatMap().get(stat).getValue();
    }

    public Set<ProfileStat> flushStats() {
        if (statMap.size() == profileStats.size()) {
            profileStats.clear();
            profileStats.addAll(statMap.values());
        }
        return profileStats;
    }

    public ArrayList<ProfileStat> getSortedProfileStats() {
        Stat[] statArr = Stat.values();
        Arrays.sort(statArr);
        ArrayList<ProfileStat> profileStats = new ArrayList<>();
        for (int i = 0; i < statArr.length; i++) profileStats.add(getStat(statArr[i]));
        return profileStats;
    }

    public boolean isAtLocation(Location location) {
        if (location == null) return false;
        if (location.getXpos().getRid() == xpos.getRid() && location.getYpos().getRid() == ypos.getRid()) {
            return true;
        }
        return false;
    }

    public boolean isAtBounty(Bounty bounty) {
        if (bounty == null) return false;
        if (bounty.getXpos().getRid() == xpos.getRid() && bounty.getYpos().getRid() == ypos.getRid()) {
            return true;
        }
        return false;
    }

    public boolean equals(Profile profile) {
        if (this.pid == profile.getPid()) {
            return true;
        }
        return false;
    }

    public float spRatio() {
        ProfileStat sp = statMap.get(Stat.STAMINA);
        return (float)sp.getValue() / (float)sp.getMax();
    }

    @Override
    public String toString() {
        return String.format(
                "Profile[pid=%d, user='%s', handle='%s', rpgclass='%s', level=%d]",
                pid, uid, handle, rpgcid.getRpgClass(), level
        );
    }
}
