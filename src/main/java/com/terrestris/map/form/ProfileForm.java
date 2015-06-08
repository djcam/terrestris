package com.terrestris.map.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * Created by dcampbell2 on 3/5/2015.
 */
public class ProfileForm {

    @NotEmpty
    private String rpgcid = new Integer(1).toString();
    @NotNull
    private String level = new Integer(1).toString();
    @NotNull
    private String handle = "";

    /*** GETTERS AND SETTERS ***/

    public String getRpgcid() {
        return rpgcid;
    }

    public void setRpgcid(String rpgcid) { this.rpgcid = rpgcid; }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) { this.level = level; }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

}
