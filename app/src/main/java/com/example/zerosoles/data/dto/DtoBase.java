package com.example.zerosoles.data.dto;

import java.sql.Timestamp;

public class DtoBase {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean active;

    public DtoBase() {
    }

    public DtoBase(Timestamp createdAt, Timestamp updatedAt, boolean active) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
