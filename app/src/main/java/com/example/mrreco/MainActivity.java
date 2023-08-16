package com.example.mrreco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnview;
    private static final String ROOT_FRAGMENT_TAG = "root_fragment";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnview = findViewById(R.id.bnView);

        bnview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_Home) {
                    loadFragment(new HomeFragment(), false);
                } else if (id == R.id.nav_search) {
                    loadFragment(new SearchFragment(), false);
                } else if (id == R.id.nav_explore) {
                    loadFragment(new exploreFragment(), false);
                } else {
                    // profile
                    loadFragment(new profileFragment(), false);
                }
                return true; // it true to show this
            }
        });
        bnview.setSelectedItemId(R.id.nav_Home);


    }

    // Function to load a fragments
    void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        // Bundle passing throwout we have to gather this information






        if (flag){
            // by default true
            ft.add(R.id.container, fragment);
            fm.popBackStack(ROOT_FRAGMENT_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack(ROOT_FRAGMENT_TAG);
        }
        else {
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(null);
        }
        ft.commit();

    }
    // **** Function end to load a fragments ***

}