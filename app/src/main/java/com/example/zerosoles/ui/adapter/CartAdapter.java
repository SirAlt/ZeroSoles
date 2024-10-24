package com.example.zerosoles.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zerosoles.R;
import com.example.zerosoles.data.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
//        holder.name.setText(cartItem.getName());
//        holder.style.setText(cartItem.getStyle());
//        holder.size.setText(cartItem.getSize());
//        holder.quantity.setText("x " + cartItem.getQuantity());
//        holder.price.setText("$" + cartItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, style, size, quantity, price;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvShoeName);
            style = itemView.findViewById(R.id.tvShoeStyle);
            size = itemView.findViewById(R.id.tvShoeSize);
            quantity = itemView.findViewById(R.id.cart_badge);
            price = itemView.findViewById(R.id.tvTotal);
        }
    }
}

