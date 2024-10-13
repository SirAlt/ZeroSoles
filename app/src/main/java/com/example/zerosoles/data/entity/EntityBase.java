package com.example.zerosoles.data.entity;

import java.sql.Timestamp;

public abstract class EntityBase {
    private long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean active;

    protected EntityBase() {}

    /**
     * @noinspection unchecked
     */
    protected static class Builder<
            TEntity extends EntityBase,
            TBuilder extends Builder<TEntity, TBuilder>> {

        protected final TEntity entity;

        protected Builder(TEntity entity) {
            this.entity = entity;
        }

        public TBuilder withId(long id) {
            ((EntityBase) entity).setId(id);
            return (TBuilder) this;
        }

        public TBuilder withCreatedAt(Timestamp createdAt) {
            ((EntityBase) entity).setCreatedAt(createdAt);
            return (TBuilder) this;
        }

        public TBuilder withUpdatedAt(Timestamp updatedAt) {
            ((EntityBase) entity).setUpdatedAt(updatedAt);
            return (TBuilder) this;
        }

        public TBuilder withActive(boolean active) {
            ((EntityBase) entity).setActive(active);
            return (TBuilder) this;
        }

        public TEntity build() {
            return entity;
        }
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    private void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
        this.active = active;
    }
}
