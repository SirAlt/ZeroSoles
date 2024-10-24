package com.example.zerosoles.data.model;

public class CartItem {
    private String userId;
    private String shoeId;
    private int quantity;

    public CartItem(String userId, String shoeId, int quantity) {
        this.userId = userId;
        this.shoeId = shoeId;
        this.quantity = quantity;
    }

    public String getUserId() { return userId; }
    public String getShoeId() { return shoeId; }
    public int getQuantity() { return quantity; }
}
