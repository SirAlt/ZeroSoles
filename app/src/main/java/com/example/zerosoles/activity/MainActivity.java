package com.example.zerosoles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.zerosoles.R;
import com.example.zerosoles.fragment.Account;
import com.example.zerosoles.fragment.Contact;
import com.example.zerosoles.fragment.Home;
import com.example.zerosoles.fragment.Logout;
import com.example.zerosoles.fragment.Order;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.black));
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        } else if (item.getItemId() == R.id.orders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Order()).commit();
        } else if (item.getItemId() == R.id.contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact()).commit();
        } else if (item.getItemId() == R.id.account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Account()).commit();
        } else if (item.getItemId() == R.id.logout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Logout()).commit();
        } else if (item.getItemId() == R.id.store) {
            Intent intent = new Intent(MainActivity.this, StoresNearYouActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
