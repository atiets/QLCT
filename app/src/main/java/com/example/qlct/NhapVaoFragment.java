package com.example.qlct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlct.databinding.FragmentNhapvaoBinding;

public class NhapVaoFragment extends Fragment {
    FragmentNhapvaoBinding binding;
    public NhapVaoFragment() {
    }
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saveInstanceState) {
        binding = FragmentNhapvaoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}