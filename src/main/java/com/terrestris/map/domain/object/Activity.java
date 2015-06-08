package com.terrestris.map.domain.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcampbell2 on 3/24/2015.
 */

public enum Activity {
    MOVES(1, "moves"),
    FINDS(2, "finds"),
    REPLENISHES(3, "replenishes"),
    DEFEATS(4, "defeats"),
    ATTACKS(5, "attacks"),
    SELLS(6, "sells"),
    BUYS(7, "buys"),
    DROPS(8, "drops"),
    USES(9, "uses");

    private int aid;
    private String activity;
    private static Map<Integer, Activity> map = new HashMap<Integer, Activity>();

    static {
        for (Activity activityEnum : Activity.values()) {
            map.put(activityEnum.aid, activityEnum);
        }
    }

    private Activity(final int aid, final String activity) {
        this.aid = aid;
        this.activity = activity;
    }

    public int getAid() {
        return aid;
    }

    public static Activity valueOf(int aid) {
        return map.get(aid);
    }

    public String getActivity() {
        return activity;
    }
}
