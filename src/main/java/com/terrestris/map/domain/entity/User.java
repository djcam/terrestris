package com.terrestris.map.domain.entity;

import com.terrestris.map.domain.object.Gender;
import com.terrestris.map.domain.object.Role;
import com.terrestris.map.domain.object.UserStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid", nullable = false, updatable = false)
    private long uid;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "first")
    private String first;

    @Column(name = "last")
    private String last;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User() {
    }

    public long getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * EO GETTERS AND SETTERS **
     */

    @Override
    public String toString() {
        return String.format(
                "User[uid=%d, email='%s', role='%s']",
                uid, email, role
        );

    }
}
