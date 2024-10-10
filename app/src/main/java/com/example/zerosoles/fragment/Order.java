package com.example.zerosoles.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zerosoles.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order extends Fragment {
    public Order() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}