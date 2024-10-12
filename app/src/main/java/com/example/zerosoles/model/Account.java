package com.example.zerosoles.model;

public class Account {
   // Enum cho role
   public enum Role {
      STAFF,
      ADMIN,
      CUSTOMER
   }

   // Enum cho gender
   public enum Gender {
      MALE,
      FEMALE,
      NON_BINARY
   }

   private long id;
   private String username;
   private String password;
   private String fullName;
   private Gender gender;
   private String email;
   private Role role;
   private boolean canShip;
   private boolean active;
   private String createdAt;
   private String updatedAt;

   // Constructor
   public Account(long id, String username, String password, String fullname, Gender gender, String email, Role role, boolean canShip, boolean active, String createdAt, String updatedAt) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.fullName = fullname;
      this.gender = gender;
      this.email = email;
      this.role = role;
      this.canShip = canShip;
      this.active = active;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
   }

   // Getter and Setter methods

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFullname() {
      return fullName;
   }

   public void setFullname(String fullname) {
      this.fullName = fullname;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public boolean isCanShip() {
      return canShip;
   }

   public void setCanShip(boolean canShip) {
      this.canShip = canShip;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
   }

   public String getUpdatedAt() {
      return updatedAt;
   }

   public void setUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
   }
}
