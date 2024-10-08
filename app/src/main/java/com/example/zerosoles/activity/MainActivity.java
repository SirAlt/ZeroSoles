package com.example.zerosoles.activity;

import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zerosoles.R;
import com.example.zerosoles.adapter.ShoeAdapter;
import com.example.zerosoles.model.Shoe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvShoes = findViewById(R.id.lvShoes);
        ArrayList<Shoe> shoes = new ArrayList<>(SHOES);
        ShoeAdapter shoeAdapter = new ShoeAdapter(MainActivity.this, R.layout.row_shoe, shoes);
        lvShoes.setAdapter(shoeAdapter);

        ImageView icMenu = findViewById(R.id.icMenu);
        icMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StoresNearYouActivity.class);
            startActivity(intent);
        });
    }

    private static final List<Shoe> SHOES = new ArrayList<>();

    static {
        SHOES.add(new Shoe(R.drawable.tidal_wave, "Tidal Wave", BigDecimal.valueOf(24.99), BigDecimal.valueOf(19.99)));
        SHOES.add(new Shoe(R.drawable.pagosa_black, "Pagosa Black", BigDecimal.valueOf(44.49), BigDecimal.valueOf(34.98)));
        SHOES.add(new Shoe(R.drawable.ridgeway_fallen_rock, "Ridgeway - Fallen Rock", BigDecimal.valueOf(19.50)));
    }
}
