package com.Kel6.nontoncuyy;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView ivThemeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupNavbar();
        setupThemeToggle();

        // Load Default Fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void setupNavbar() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_search) {
                loadFragment(new SearchFragment());
                return true;
            } else if (itemId == R.id.nav_watchlist) {
                loadFragment(new WatchlistFragment());
                return true;
            }
            return false;
        });
    }

    public void openViewAll(String title, List<Film> movies) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, ViewAllFragment.newInstance(title, movies));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openDetail(Film film) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, DetailFragment.newInstance(film));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setupThemeToggle() {
        ivThemeToggle = findViewById(R.id.ivThemeToggle);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            ivThemeToggle.setImageResource(R.drawable.ic_sun);
        } else {
            ivThemeToggle.setImageResource(R.drawable.ic_moon);
        }

        ivThemeToggle.setOnClickListener(v -> {
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }
}
