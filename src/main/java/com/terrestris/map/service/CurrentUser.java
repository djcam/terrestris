package com.terrestris.map.service;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Gender;
import com.terrestris.map.domain.object.Role;
import com.terrestris.map.domain.object.UserStatus;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by dcampbell2 on 2/26/2015.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;
    private Profile profile;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getUid();
    }

    public String getEmail() { return user.getEmail(); }

    public Role getRole() {
        return user.getRole();
    }

    public Gender getGender() { return user.getGender(); }

    public UserStatus getStatus() { return user.getStatus(); }

    public String getFirst() { return user.getFirst(); }

    public String getLast() { return user.getLast(); }

    public String getName() { return getFirst().concat(" ").concat(getLast()); }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

}
