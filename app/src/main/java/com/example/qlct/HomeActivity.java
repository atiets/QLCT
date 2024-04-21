package com.example.qlct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.qlct.databinding.HomeActivityBinding;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityBinding binding;
    private ViewPager2Adapter viewPager2Adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Nhập vào"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lịch"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Báo Cáo"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager2Adapter = new ViewPager2Adapter(fragmentManager, getLifecycle());
        binding.viewPager2.setAdapter(viewPager2Adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }
}
