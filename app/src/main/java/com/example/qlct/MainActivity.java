package com.example.qlct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Fragment.ChartFragment;
import com.example.qlct.Fragment.ChiFragment;
import com.example.qlct.Fragment.DateFragment;
import com.example.qlct.Fragment.InforFragment;
import com.example.qlct.Fragment.SettingFragment;
import com.example.qlct.Fragment.ShareFragment;
import com.example.qlct.Fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_thu);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_thu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThuFragment()).commit();
        } else if (itemId == R.id.nav_chi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChiFragment()).commit();
        } else if (itemId == R.id.nav_date) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DateFragment()).commit();
        } else if (itemId == R.id.nav_chart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChartFragment()).commit();
        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
        } else if (itemId == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();
        } else if (itemId == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InforFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
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