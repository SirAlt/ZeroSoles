package com.example.zerosoles.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;

import java.util.List;
import java.util.Locale;

public class ShoesAdapter extends ArrayAdapter<ShoesDto> {

    public ShoesAdapter(@NonNull Context context, int resource, @NonNull List<ShoesDto> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_shoe, parent, false);
        }

        ShoesDto shoes = getItem(position);
        assert shoes != null;

        ImageView imgShoe = convertView.findViewById(R.id.imgShoe);
        imgShoe.setImageResource(shoes.getImageId());

        TextView tvShoeName = convertView.findViewById(R.id.tvShoeName);
        tvShoeName.setText(shoes.getName());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        TextView tvShoePrice = convertView.findViewById(R.id.tvShoePrice);
        BigDecimal price = shoes.getPrice();
        BigDecimal displayPrice = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        String priceText = numberFormat.format(displayPrice);
        tvShoePrice.setText(priceText);

        TextView tvShoeNewPrice = convertView.findViewById(R.id.tvShoeNewPrice);
        BigDecimal newPrice = shoes.getNewPrice();
        if (newPrice != null) {
            int disabledColor = ContextCompat.getColor(context, R.color.grey_disabled);
            tvShoePrice.setTextColor(disabledColor);
            tvShoePrice.setPaintFlags(tvShoePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            BigDecimal displayNewPrice = newPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            String newPriceText = numberFormat.format(displayNewPrice);
            tvShoeNewPrice.setText(newPriceText);
        } else {
            int blackColor = ContextCompat.getColor(context, R.color.black);
            tvShoePrice.setTextColor(blackColor);
            tvShoePrice.setPaintFlags(tvShoePrice.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            tvShoeNewPrice.setText("");
        }

        return convertView;
    }
}
