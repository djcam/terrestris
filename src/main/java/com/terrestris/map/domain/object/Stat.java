package com.terrestris.map.domain.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcampbell2 on 4/22/2015.
 */
public enum Stat {
    STAMINA(0, "Stamina", "sp", "green", "icon-aid-kit"),
    MANA(1, "Mana", "mp", "blue", "icon-magic-wand"),
    EXPERIENCE(2, "Experience", "xp", "orange", "icon-star-full");

    private int stid;
    private String title;
    private String abbrev;
    private String color;
    private String icon;
    private static Map<Integer, Stat> map = new HashMap<>();

    static {
        for (Stat stat : Stat.values()) {
            map.put(stat.stid, stat);
        }
    }

    private Stat(int stid, String title, String abbrev, String color, String icon) {
        this.stid = stid;
        this.title = title;
        this.abbrev = abbrev;
        this.color = color;
        this.icon = icon;
    }

    public static Stat valueOf(int stid) {
        return map.get(stid);
    }

    public int getStid() {
        return stid;
    }

    public String getTitle() {
        return title;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getColor() {
        return color;
    }

    public String getIcon() { return icon; }

    public static Map<Integer, Stat> getMap() {
        return map;
    }
}
