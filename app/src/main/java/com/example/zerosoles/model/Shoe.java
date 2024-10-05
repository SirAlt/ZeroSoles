package com.example.zerosoles.model;

import android.icu.math.BigDecimal;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Shoe {
    private @DrawableRes int imageId;
    private String name;
    private @NonNull BigDecimal price;
    private @Nullable BigDecimal newPrice;

    public Shoe(int imageId, String name, @NonNull BigDecimal price) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
    }

    public Shoe(int imageId, String name, @NonNull BigDecimal price, @Nullable BigDecimal newPrice) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.newPrice = newPrice;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NonNull BigDecimal price) {
        this.price = price;
    }

    @Nullable
    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(@Nullable BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
