package com.example.zerosoles.ui.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerosoles.R;

public class CartActivity extends AppCompatActivity {
    ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        btnClose = findViewById(R.id.btn_close);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        btnClose.setOnClickListener(v -> {
            finish();
        });

    }
}