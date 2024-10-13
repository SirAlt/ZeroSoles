package com.example.zerosoles.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ShoesViewHolder> {

    private List<ShoesDto> shoeList;
    private Context context;

    public ShoesAdapter(Context context, List<ShoesDto> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @NonNull
    @Override
    public ShoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shoe, parent, false);
        return new ShoesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesViewHolder holder, int position) {
        ShoesDto shoe = shoeList.get(position);
        holder.imgShoe.setImageResource(shoe.getImageId());
        holder.tvShoeName.setText(shoe.getName());
    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    // Lớp ViewHolder để ánh xạ các View
    public static class ShoesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShoe;
        TextView tvShoeName;

        public ShoesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShoe = itemView.findViewById(R.id.imgShoe);
            tvShoeName = itemView.findViewById(R.id.tvShoeName);
        }
    }
}
