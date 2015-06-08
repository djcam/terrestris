package com.terrestris.map.domain.object;

import com.terrestris.map.domain.entity.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dcampbell2 on 5/13/2015.
 */
public enum Perk {
    TELEPORT(0, "teleport", RpgClass.ANGEL, 2 * 3600, 5),
    DRAIN(1, "drain", RpgClass.DEMON, 2 * 3600, 5),
    LARCENY(2, "larceny", RpgClass.ASSASSIN, 2 * 3600, 5),
    DESTRUCTION(3, "frenzy", RpgClass.WARRIOR, 2 * 3600, 5),
    CONSTRUCTION(4, "construction", RpgClass.MACHINIST, 2 * 3600, 5),
    REJUVENATION(5, "rejuvenation", RpgClass.ANGEL, 4 * 3600, 15),
    ANNIHILATION(6, "annhilation", RpgClass.DEMON, 4 * 3600, 15),
    INVISIBILITY(7, "invisibility", RpgClass.ASSASSIN, 4 * 3600, 15),
    UNSURMOUNTABLE(8, "unsurmountable", RpgClass.WARRIOR, 4 * 3600, 15),
    PERCEPTION(9, "perception", RpgClass.MACHINIST, 4 * 3600, 15);

    private int perkid;
    private String perk;

    private RpgClass rpgcid;
    private int minRate;
    private int level;

    private static Map<Integer, Perk> map = new HashMap<>();
    static {
        for (Perk perk : Perk.values()) {
            map.put(perk.perkid, perk);
        }
    }

    private Perk(int perkid, String perk, RpgClass rpgcid, int minRate, int level) {
        this.perkid = perkid;
        this.perk = perk;
        this.rpgcid = rpgcid;
        this.minRate = minRate;
        this.level = level;
    }

    public static Perk valueOf(int perkid) {
        return map.get(perkid);
    }
    public static Map<Integer, Perk> getMap() { return map; }

    public int getPerkid() {
        return perkid;
    }

    public String getPerk() {
        return perk;
    }

    public RpgClass getRpgcid() {
        return rpgcid;
    }

    public int getMinRate() {
        return minRate;
    }

    public int getLevel() {
        return level;
    }
}
