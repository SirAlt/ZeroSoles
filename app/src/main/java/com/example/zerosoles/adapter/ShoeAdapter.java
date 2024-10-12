package com.example.zerosoles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.model.Shoe;

import java.util.List;

public class ShoeAdapter extends RecyclerView.Adapter<ShoeAdapter.ShoeViewHolder> {

    private List<Shoe> shoeList;
    private Context context;

    public ShoeAdapter(Context context, List<Shoe> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @NonNull
    @Override
    public ShoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shoe, parent, false);
        return new ShoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoeViewHolder holder, int position) {
        Shoe shoe = shoeList.get(position);
        holder.imgShoe.setImageResource(shoe.getImageId());
        holder.tvShoeName.setText(shoe.getName());
    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    // Lớp ViewHolder để ánh xạ các View
    public static class ShoeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShoe;
        TextView tvShoeName;

        public ShoeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShoe = itemView.findViewById(R.id.imgShoe);
            tvShoeName = itemView.findViewById(R.id.tvShoeName);
        }
    }
}
