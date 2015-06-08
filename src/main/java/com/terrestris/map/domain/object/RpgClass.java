package com.terrestris.map.domain.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcampbell2 on 5/13/2015.
 */
public enum RpgClass {
    ANGEL(0, "angel"),
    DEMON(1, "demon"),
    ASSASSIN(2, "assassin"),
    WARRIOR(3, "warrior"),
    MACHINIST(4, "machinist");

    private int rpgcid;
    private String rpgClass;

    RpgClass(int rpgcid, String rpgClass) {
        this.rpgcid = rpgcid;
        this.rpgClass = rpgClass;
    }

    private static Map<Integer, RpgClass> map = new HashMap<>();
    static {
        for (RpgClass rpgClass : RpgClass.values()) {
            map.put(rpgClass.rpgcid, rpgClass);
        }
    }

    public static RpgClass valueOf(int rpgcid) {
        return map.get(rpgcid);
    }
    public static Map<Integer, RpgClass> getMap() { return map; }

    public int getRpgcid() {
        return rpgcid;
    }

    public String getRpgClass() {
        return rpgClass;
    }
}
