package com.example.qlct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Fragment.ChartFragment;
import com.example.qlct.Fragment.ChiFragment;
import com.example.qlct.Fragment.DateFragment;
import com.example.qlct.Fragment.InforFragment;
import com.example.qlct.Fragment.SettingFragment;
import com.example.qlct.Fragment.ShareFragment;
import com.example.qlct.Fragment.ThuFragment;
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.KhoanThu;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        /*databaseHandler = new DatabaseHandler(this);
        databaseHandler.deleteAllDataFromKhoanChiTable();
        databaseHandler.deleteAllDataFromKhoanThuTable();
        databaseHandler.deleteAllDataFromLoaiChiTable();
        databaseHandler.deleteAllDataFromLoaiThuTable();
        List<LoaiChi> loaiChiList = new ArrayList<>();
        loaiChiList.add(new LoaiChi(1,"Mua sam"));
        loaiChiList.add(new LoaiChi(2,"Du lich"));
        loaiChiList.add(new LoaiChi(3,"Gia dinh"));

        for (LoaiChi loaiChi : loaiChiList) {
            databaseHandler.addLoaiChi(loaiChi);
        }

        List<KhoanChi> khoanChiList = new ArrayList<>();
        khoanChiList.add(new KhoanChi(1, "Mua vay", "Mua sam", "2024-04-17", 50000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(2, "Mua nuoc hoa", "Mua sam", "2024-04-17", 10000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(3, "Mua do an vat", "Mua sam", "2024-04-17", 20000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(4, "Mua mu", "Gia dinh", "2024-04-17", 100000, 0, 3, 3));
        khoanChiList.add(new KhoanChi(5, "Mua ve may bay", "Du lich", "2024-04-17", 500000, 0, 2, 2));

        khoanChiList.add(new KhoanChi(6, "Mua ao ", "Mua sam", "2024-05-17", 20000, 0, 2, 1));
        khoanChiList.add(new KhoanChi(7, "Mua ve may bay", "Du lich", "2024-05-17", 500000, 0, 2, 3));

        khoanChiList.add(new KhoanChi(8, "Mua vay", "Mua sam", "2025-04-17", 50000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(9, "Mua nuoc hoa", "Mua sam", "2025-04-17", 10000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(10, "Mua ao cho me", "Gia dinh", "2025-04-17", 20000, 0, 1, 3));

        khoanChiList.add(new KhoanChi(11, "Mua mu", "Gia dinh", "2023-04-17", 100000, 0, 3, 3));
        khoanChiList.add(new KhoanChi(12, "Mua ve may bay", "Du lich", "2023-04-17", 500000, 0, 2, 2));

        khoanChiList.add(new KhoanChi(13, "Mua vay", "Mua sam", "2026-04-17", 50000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(14, "Mua nuoc hoa", "Mua sam", "2026-04-17", 10000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(15, "Mua do an vat", "Mua sam", "2026-04-17", 20000, 0, 1, 1));
        khoanChiList.add(new KhoanChi(16, "Mua mu", "Gia dinh", "2026-04-17", 100000, 0, 3, 3));
        khoanChiList.add(new KhoanChi(17, "Mua ve may bay", "Du lich", "2026-04-17", 500000, 0, 2, 2));


        for (KhoanChi khoanChi : khoanChiList) {
            databaseHandler.addKhoanChi(khoanChi);
        }

        List<LoaiThu> loaiThuList = new ArrayList<>();
        loaiThuList.add(new LoaiThu(1,"Luong"));
        loaiThuList.add(new LoaiThu(2,"Tro Cap"));
        loaiThuList.add(new LoaiThu(3,"Lam them"));
        for (LoaiThu loaiThu : loaiThuList) {
            databaseHandler.addLoaiThu(loaiThu);
        }
        List<KhoanThu> khoanThuList = new ArrayList<>();
        khoanThuList.add(new KhoanThu(1, "Luong thang 11", "Luong", "2024-04-17", 10000, 0, 0, 1));
        khoanThuList.add(new KhoanThu(2, "Phuc vu", "Lam them", "2024-04-17", 450000, 0, 0, 3));
        khoanThuList.add(new KhoanThu(3, "Gia su", "Làm them", "2024-04-17", 460000, 0, 0, 3));
        khoanThuList.add(new KhoanThu(4, "Tro cap xang", "Tro cap", "2024-04-17", 200000, 0, 0, 2));

        khoanThuList.add(new KhoanThu(5, "Tro cao xang", "Tro cap", "2024-05-17", 200000, 0, 0, 2));
        khoanThuList.add(new KhoanThu(6, "Phuc vu", "Lam them", "2024-05-17", 500000, 0, 0, 3));

        for (KhoanThu khoanThu : khoanThuList) {
            databaseHandler.addKhoanThu(khoanThu);
        }*/
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
        } else if (itemId == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InforFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            thongBaoLogOut();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void thongBaoLogOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bạn có muốn đăng xuất?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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