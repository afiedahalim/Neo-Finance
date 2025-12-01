package com.example.vehicleloan;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the ActionBar title so "Home/Calculator/About" doesn't show on top
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            openFragment(new HomeFragment(), getString(R.string.home));
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            String title;

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
                title = getString(R.string.home);
            } else if (id == R.id.nav_calculator) {
                fragment = new CalculatorFragment();
                title = getString(R.string.calculator);
            } else {
                fragment = new AboutFragment();
                title = getString(R.string.about);
            }

            openFragment(fragment, title);
            return true;
        });

    }

    /**
     * Made public so fragments (HomeFragment) can call it to change fragments with animation.
     */
    public void openFragment(@NonNull Fragment fragment, @NonNull String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_right,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        );
        ft.replace(R.id.fragment_container, fragment).commit();

        // We keep the ActionBar title hidden as requested; if you want a small title,
        // you can update a TextView inside the toolbar instead.
    }
}