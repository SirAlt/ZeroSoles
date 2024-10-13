package com.example.zerosoles.fragment;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;
import com.example.zerosoles.ui.adapter.ShoesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private static final List<ShoesDto> SHOES = new ArrayList<>();

    static {
        SHOES.add(new ShoesDto(R.drawable.shoes, "Tidal Wave", BigDecimal.valueOf(24.99), BigDecimal.valueOf(19.99)));
        SHOES.add(new ShoesDto(R.drawable.shoes2, "Pagosa Black", BigDecimal.valueOf(44.49), BigDecimal.valueOf(34.98)));
        SHOES.add(new ShoesDto(R.drawable.shoes3, "Ridgeway - Fallen Rock", BigDecimal.valueOf(19.50)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerViewShoes = view.findViewById(R.id.recyclerViewShoes);
        RecyclerView recyclerViewShoes2 = view.findViewById(R.id.recyclerViewShoes2);
        recyclerViewShoes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewShoes2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ShoesAdapter shoesAdapter = new ShoesAdapter(getActivity(), new ArrayList<>(SHOES));
        recyclerViewShoes.setAdapter(shoesAdapter);
        recyclerViewShoes2.setAdapter(shoesAdapter);

        return view;
    }
}
