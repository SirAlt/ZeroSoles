package com.example.zerosoles.ui.fragment;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zerosoles.R;
import com.example.zerosoles.data.dto.ShoesDto;
import com.example.zerosoles.ui.adapter.CategoryAdapter;
import com.example.zerosoles.ui.adapter.ProductImageAdapter;
import com.example.zerosoles.ui.adapter.ShoesAdapter;
import com.example.zerosoles.ui.adapter.ShoesAdapter2;

import java.util.Arrays;
import java.util.List;

public class MenFragment extends Fragment {

    private RecyclerView categoryList, shoeList, productImageRecycler;
    private CategoryAdapter categoryAdapter;

    public MenFragment() {
        // Required empty public constructor
    }

    public static MenFragment newInstance(String param1, String param2) {
        MenFragment fragment = new MenFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_men, container, false);

        categoryList = view.findViewById(R.id.categoryList);
        categoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<String> categories = Arrays.asList("Category 1", "Category 2", "Category 3", "Category 4");

        categoryAdapter = new CategoryAdapter(categories);
        categoryList.setAdapter(categoryAdapter);

        shoeList = view.findViewById(R.id.shoeList);
        shoeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Create mock shoe data
        List<ShoesDto> shoes = Arrays.asList(
                new ShoesDto(R.drawable.shoes2, "Shoe 1", BigDecimal.valueOf(99.99)), // Giá giày
                new ShoesDto(R.drawable.shoes2, "Shoe 2", BigDecimal.valueOf(89.99), BigDecimal.valueOf(79.99)), // Giá cũ
                new ShoesDto(R.drawable.shoes3, "Shoe 3", BigDecimal.valueOf(49.99))
        );

        // Set up the ShoesAdapter
        ShoesAdapter2 shoesAdapter = new ShoesAdapter2(getContext(), shoes);
        shoeList.setAdapter(shoesAdapter);

        productImageRecycler = view.findViewById(R.id.productImageRecycler);
//        productImageRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Create mock shoe data
        List<Integer> shoeImg = Arrays.asList(
                R.drawable.shoes, // Thay thế bằng các ID hình ảnh thật
                R.drawable.shoes2,
                R.drawable.shoes3
        );

        // Set up the ShoesAdapter
//        ProductImageAdapter productImageAdapter = new ProductImageAdapter(getContext(), shoeImg);
//        productImageRecycler.setAdapter(productImageAdapter);

        return view;
    }
}
