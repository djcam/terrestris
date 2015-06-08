package com.terrestris.map.domain.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcampbell2 on 4/20/2015.
 */
public enum InventoryType {
    CONSUMABLE(0, "Consumable"),
    VALUABLE(1, "Valuable"),
    QUEST(2, "Quest"),
    ACTION(3, "Action");

    private int itypeid;
    private String formatted;
    private static Map<Integer, InventoryType> map = new HashMap<Integer, InventoryType>();

    static {
        for (InventoryType invEnum : InventoryType.values()) {
            map.put(invEnum.itypeid, invEnum);
        }
    }

    private InventoryType(final int itypeid, final String formatted) {
        this.itypeid = itypeid;
        this.formatted = formatted;
    }

    public int getItypeid() {
        return itypeid;
    }

    public static InventoryType valueOf(int itypeid) {
        return map.get(itypeid);
    }

    public String getFormatted() {
        return formatted;
    }
}
