package com.example.zerosoles.ui.fragment;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;
import com.example.zerosoles.ui.adapter.ShoesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private static final List<ShoesDto> SHOES = new ArrayList<>();

    static {
        SHOES.add(new ShoesDto(R.drawable.tidal_wave, "Tidal Wave", BigDecimal.valueOf(24.99), BigDecimal.valueOf(19.99)));
        SHOES.add(new ShoesDto(R.drawable.pagosa_black, "Pagosa Black", BigDecimal.valueOf(44.49), BigDecimal.valueOf(34.98)));
        SHOES.add(new ShoesDto(R.drawable.ridgeway_fallen_rock, "Ridgeway - Fallen Rock", BigDecimal.valueOf(19.50)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView lvShoes = view.findViewById(R.id.recyclerViewShoes);
        ShoesAdapter shoesAdapter = new ShoesAdapter(getActivity(), new ArrayList<>(SHOES));
        lvShoes.setAdapter(shoesAdapter);

        return view;
    }
}
