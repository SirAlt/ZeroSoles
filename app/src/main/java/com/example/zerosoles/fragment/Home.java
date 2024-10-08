package com.example.zerosoles.fragment;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.zerosoles.R;
import com.example.zerosoles.adapter.ShoeAdapter;
import com.example.zerosoles.model.Shoe;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private static final List<Shoe> SHOES = new ArrayList<>();

    static {
        SHOES.add(new Shoe(R.drawable.tidal_wave, "Tidal Wave", BigDecimal.valueOf(24.99), BigDecimal.valueOf(19.99)));
        SHOES.add(new Shoe(R.drawable.pagosa_black, "Pagosa Black", BigDecimal.valueOf(44.49), BigDecimal.valueOf(34.98)));
        SHOES.add(new Shoe(R.drawable.ridgeway_fallen_rock, "Ridgeway - Fallen Rock", BigDecimal.valueOf(19.50)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView lvShoes = view.findViewById(R.id.lvShoes);
        ShoeAdapter shoeAdapter = new ShoeAdapter(getActivity(), R.layout.row_shoe, new ArrayList<>(SHOES));
        lvShoes.setAdapter(shoeAdapter);

        return view;
    }
}
