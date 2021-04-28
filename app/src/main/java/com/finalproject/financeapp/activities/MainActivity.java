package com.finalproject.financeapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.finalproject.financeapp.R;
import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.databinding.MainActivityBinding;
import com.finalproject.financeapp.fragments.HistoryFragment;


public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private FragmentManager fragmentManager;
    public final static String dataName = "database.db";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase.getInstance(this);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        openFragment(new HistoryFragment());
        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.history:
                    openFragment(new HistoryFragment());
                    return true;
                case R.id.statisticMonth:
                    return true;
                case R.id.statisticYear:
                    return true;
            }
            return false;
        });
        setContentView(binding.getRoot());
    }

    public void openFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity, fragment)
                .addToBackStack(null)
                .commit();
    }
}