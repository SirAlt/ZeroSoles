package com.example.zerosoles.ui.fragment;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;
import com.example.zerosoles.ui.adapter.ShoesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    TextView tvShopAllWomen, tvShopAllMen;
    NavController navController;

    private static final List<ShoesDto> SHOES = new ArrayList<>();

    static {
        SHOES.add(new ShoesDto(R.drawable.shoes, "Tidal Wave", BigDecimal.valueOf(24.99), BigDecimal.valueOf(19.99)));
        SHOES.add(new ShoesDto(R.drawable.shoes2, "Pagosa Black", BigDecimal.valueOf(44.49), BigDecimal.valueOf(34.98)));
        SHOES.add(new ShoesDto(R.drawable.shoes3, "Ridgeway - Fallen Rock", BigDecimal.valueOf(19.50)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvShopAllMen = view.findViewById(R.id.tvShopAllMen);
        tvShopAllWomen = view.findViewById(R.id.tvShopAllWomen);

        RecyclerView recyclerViewShoes = view.findViewById(R.id.recyclerViewShoes);
        RecyclerView recyclerViewShoes2 = view.findViewById(R.id.recyclerViewShoes2);
        recyclerViewShoes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewShoes2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ShoesAdapter shoesAdapter = new ShoesAdapter(getActivity(), new ArrayList<>(SHOES));
        recyclerViewShoes.setAdapter(shoesAdapter);
        recyclerViewShoes2.setAdapter(shoesAdapter);

//        tvShopAllMen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("HomeFragment", "Shop All Men Clicked");
//                Toast.makeText(getActivity(), "Shop All Men Clicked", Toast.LENGTH_SHORT).show();
//                try {
//                    navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//
//                    navController.navigate(R.id.action_home_to_menFragment);
//                } catch (Exception e) {
//                    Log.e("HomeFragment", "Error navigating to Men Fragment: " + e.getMessage());
//                }
//            }
//        });
        tvShopAllMen.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Shop All Men Clicked", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new MenFragment())
                    .addToBackStack(null)
                    .commit();
        });


        tvShopAllWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeFragment", "Shop All Women Clicked");
                try {
                    navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

                    navController.navigate(R.id.action_home_to_womenFragment);
                } catch (Exception e) {
                    Log.e("HomeFragment", "Error navigating to Women Fragment: " + e.getMessage());
                }
            }
        });


        return view;
    }
}
