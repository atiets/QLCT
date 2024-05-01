package com.example.qlct.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.qlct.Fragment.KhoanThuFragment;
import com.example.qlct.Fragment.LoaiThuFragment;


public class PageThuAdapter extends FragmentPagerAdapter {
    public PageThuAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhoanThuFragment();
            case 1:
                return new LoaiThuFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Khoản Thu";
            case 1:
                return "Loại Thu";
            default:
                return null;
        }
    }
}