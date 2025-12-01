package com.example.vehicleloan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    private MaterialButton btnOpenCalculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnOpenCalculator = root.findViewById(R.id.btnOpenCalculator);

        btnOpenCalculator.setOnClickListener(v -> {
            CalculatorFragment calculatorFragment = new CalculatorFragment();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
            );
            transaction.replace(R.id.fragment_container, calculatorFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return root;
    }
}