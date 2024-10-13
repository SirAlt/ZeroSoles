package com.example.zerosoles.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @noinspection DataFlowIssue
 */
public class User extends EntityBase {
    private @NonNull String username = null;
    private @NonNull String passwordHash = null;
    private @Nullable String fullname;
    private @Nullable String email;
    private @NonNull Gender gender = Gender.OTHER;
    private @NonNull Role role = Role.CUSTOMER;
    private boolean canShip;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER,
    }

    public enum Role {
        ADMIN,
        STAFF,
        CUSTOMER,
    }

    private User() {}

    public static class Builder extends EntityBase.Builder<User, Builder> {

        public Builder() {
            super(new User());
        }

        public Builder withUsername(String username) {
            entity.setUsername(username);
            return this;
        }

        public Builder withPasswordHash(String passwordHash) {
            entity.setPasswordHash(passwordHash);
            return this;
        }

        public Builder withFullname(String fullname) {
            entity.setFullname(fullname);
            return this;
        }

        public Builder withEmail(String email) {
            entity.setEmail(email);
            return this;
        }

        public Builder withGender(Gender gender) {
            entity.setGender(gender);
            return this;
        }

        public Builder withRole(Role role) {
            entity.setRole(role);
            return this;
        }

        public Builder withCanShip(boolean canShip) {
            entity.setCanShip(canShip);
            return this;
        }
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NonNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Nullable
    public String getFullname() {
        return fullname;
    }

    public void setFullname(@Nullable String fullname) {
        this.fullname = fullname;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @NonNull
    public Gender getGender() {
        return gender;
    }

    public void setGender(@NonNull Gender gender) {
        this.gender = gender;
    }

    @NonNull
    public Role getRole() {
        return role;
    }

    public void setRole(@NonNull Role role) {
        this.role = role;
    }

    public boolean isCanShip() {
        return canShip;
    }

    public void setCanShip(boolean canShip) {
        this.canShip = canShip;
    }
}
