package com.terrestris.map.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dcampbell2 on 2/25/2015.
 */

@MappedSuperclass
public abstract class Road implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rid;
    protected String rname;
    protected String suffix;

    public Road() {}

    public Road(String rname, String suffix) {
        this.rname = rname;
        this.suffix = suffix;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public long getRid() {
        return rid;
    }

    @Override
    public String toString() {
        String road = String.format(
                "Road[rid=%d, rname='%s', suffix='%s']",
                rid, rname, suffix
        );
        return road;
    }
}
