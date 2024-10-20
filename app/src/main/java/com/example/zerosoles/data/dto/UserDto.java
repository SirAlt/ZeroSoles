package com.example.zerosoles.data.dto;

import java.sql.Timestamp;

public class UserDto extends DtoBase {
    private String username;
    private String passwordHash;
    private String fullname;
    private String email;
    private String gender;
    private String role;
    private boolean canShip;

    public UserDto(String username, String passwordHash, String fullname, String email, String gender, String role, boolean canShip) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullname = fullname;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.canShip = canShip;
    }

    public UserDto(String username, String passwordHash, String fullname, String email, String gender, String role, boolean canShip, Timestamp createdAt, Timestamp updatedAt, boolean active) {
        super(createdAt, updatedAt, active);
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullname = fullname;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.canShip = canShip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCanShip() {
        return canShip;
    }

    public void setCanShip(boolean canShip) {
        this.canShip = canShip;
    }
}
