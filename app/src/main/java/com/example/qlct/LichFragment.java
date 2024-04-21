package com.example.qlct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlct.databinding.FragmentLichBinding;

public class LichFragment extends Fragment {
    FragmentLichBinding binding;
    public LichFragment() {
    }
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saveInstanceState) {
        binding = FragmentLichBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
