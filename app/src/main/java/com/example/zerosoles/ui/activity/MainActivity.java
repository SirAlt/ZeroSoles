package com.example.zerosoles.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.zerosoles.R;
import com.example.zerosoles.auth.SessionManager;
import com.example.zerosoles.data.entity.User;
import com.example.zerosoles.ui.fragment.Contact;
import com.example.zerosoles.ui.fragment.Home;
import com.example.zerosoles.ui.fragment.Login;
import com.example.zerosoles.ui.fragment.Order;
import com.example.zerosoles.ui.fragment.Profile;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SessionManager.LoggedInUserChangedHandler {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ImageButton btnSearch, btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(null);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btnSearch = findViewById(R.id.search_button);
        btnCart = findViewById(R.id.cart_button);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.black));
        toggle.syncState();

        SessionManager.getInstance().addListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Home()).commit();
            navigationView.setCheckedItem(R.id.action_home);
        }

        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Cart button clicked");
                Toast.makeText(MainActivity.this, "Cart button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Home()).commit();
        } else if (item.getItemId() == R.id.action_orders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Order()).commit();
        } else if (item.getItemId() == R.id.action_contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Contact()).commit();
        } else if (item.getItemId() == R.id.action_account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Profile()).commit();
        } else if (item.getItemId() == R.id.action_login) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Login()).commit();
        } else if (item.getItemId() == R.id.action_logout) {
            handleLogoutAction();
        } else if (item.getItemId() == R.id.action_map) {
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

    @Override
    public void handleLoggedInUserChanged(@Nullable User user) {
        runOnUiThread(() -> {
            navigationView.getMenu().clear();
            if (user == null) {
                navigationView.inflateMenu(R.menu.menu_guest);
                navigationView.getMenu().performIdentifierAction(R.id.action_home, 0);
                return;
            }
            switch (user.getRole()) {
                case CUSTOMER:
                    navigationView.inflateMenu(R.menu.menu_customer);
                    navigationView.getMenu().performIdentifierAction(R.id.action_home, 0);
                    break;
                case STAFF:
                    // TODO: Staff menu
                    break;
                case ADMIN:
                    // TODO: Admin menu
                    break;
            }
        });
    }

    private void handleLogoutAction() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.prompt_confirm_logout)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> SessionManager.getInstance().setLoggedInUser(null))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }
}
