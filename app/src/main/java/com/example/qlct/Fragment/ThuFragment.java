package com.example.qlct.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlct.Adapter.PageThuAdapter;
import com.example.qlct.R;
import com.google.android.material.tabs.TabLayout;

public class ThuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thu, null);
        final TabLayout tabLayout = v.findViewById(R.id.main_tab);
        final ViewPager viewPager = v.findViewById(R.id.main_viewpager);
        PageThuAdapter pageAdapter = new PageThuAdapter(getChildFragmentManager());
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        return v;
    }
}