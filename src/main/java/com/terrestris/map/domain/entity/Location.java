package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.RpgClass;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dcampbell2 on 3/30/2015.
 */

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long lid;

    @Basic(optional = false)
    @Column(name = "lstamp", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lstamp;

    @ManyToOne
    @JoinColumn(name = "xpos", referencedColumnName = "rid")
    private Longitude xpos;

    @ManyToOne
    @JoinColumn(name = "ypos", referencedColumnName = "rid")
    private Latitude ypos;

    private long llife;
    private String lname;
    private int ltype;
    private int markup;
    private RpgClass rpgcid;

    public long getLid() {
        return lid;
    }

    public RpgClass getRpgcid() {
        return rpgcid;
    }

    public void setRpgcid(RpgClass rpgcid) {
        this.rpgcid = rpgcid;
    }

    public Date getLstamp() {
        return lstamp;
    }

    public void setLstamp(Date lstamp) {
        this.lstamp = lstamp;
    }

    public long getLlife() {
        return llife;
    }

    public void setLlife(long llife) {
        this.llife = llife;
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

    public void setYpos(Latitude ypos) {
        this.ypos = ypos;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getLtype() {
        return ltype;
    }

    public void setLtype(int ltype) {
        this.ltype = ltype;
    }

    public float getMarkup() {
        return (float)markup;
    }

    public void setMarkup(int markup) {
        this.markup = markup;
    }

    @Override
    public String toString() {
        return String.format("Location: '%s'", lname);
    }
}
