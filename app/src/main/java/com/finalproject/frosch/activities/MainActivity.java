package com.finalproject.frosch.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.databinding.MainActivityBinding;
import com.finalproject.frosch.fragments.HistoryFragment;
import com.finalproject.frosch.fragments.MonthStatisticFragment;


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
            switch (item.getItemId()) {
                case R.id.history:
                    openFragment(new HistoryFragment());
                    return true;
                case R.id.statisticMonth:
                    openFragment(new MonthStatisticFragment());
                    return true;
                case R.id.settings:
//                    openFragment(new SettingsFragment());
                    Toast.makeText(MainActivity.this, "Настройки в разработке", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.activity.getLayoutParams();
        params.setMargins(0, 0, 0, binding.navigation.getHeight());
        binding.activity.setLayoutParams(params);

        setContentView(binding.getRoot());
    }

    public void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(binding.navigation.getSelectedItemId() == R.id.history){
            finish();
        } else{
            binding.navigation.setSelectedItemId(R.id.history);
        }
    }
}
