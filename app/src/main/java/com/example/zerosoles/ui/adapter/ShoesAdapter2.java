package com.example.zerosoles.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;

import java.util.List;

public class ShoesAdapter2 extends RecyclerView.Adapter<ShoesAdapter2.ShoesViewHolder2> {

    private List<ShoesDto> shoeList;
    private Context context;

    public ShoesAdapter2(Context context, List<ShoesDto> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @NonNull
    @Override
    public ShoesViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ShoesViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesViewHolder2 holder, int position) {
        ShoesDto shoe = shoeList.get(position);
        holder.productImage.setImageResource(shoe.getImageId());
        holder.productTitle.setText(shoe.getName());
        holder.productPrice.setText("$" + shoe.getPrice());
    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    // Lớp ViewHolder cho layout mới
    public static class ShoesViewHolder2 extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;

        public ShoesViewHolder2(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
