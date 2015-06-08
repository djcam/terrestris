package com.terrestris.map.domain.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcampbell2 on 4/2/2015.
 */
public enum BountyName {
    MAVERICK("maverick", 15),
    SUCCUBUS("succubus", 14),
    KRAKEN("kraken", 13);

    private String bname;
    private int iid;
    private static HashMap<String, BountyName> map = new HashMap<>();

    static {
        for (BountyName bnameEnum : BountyName.values()) {
            map.put(bnameEnum.getBname(), bnameEnum);
        }
    }
    private BountyName(String bname, int iid) {
        this.bname = bname;
        this.iid = iid;
    }

    public static Map<String, BountyName> getMap() { return map; }

    public String getBname() {
        return bname;
    }
    public int getIid() { return iid; }
}
