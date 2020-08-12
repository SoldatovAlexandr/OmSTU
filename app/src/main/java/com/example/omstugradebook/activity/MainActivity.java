package com.example.omstugradebook.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omstugradebook.R;
import com.example.omstugradebook.fragments.AccountFragment;
import com.example.omstugradebook.fragments.GradeFragment;
import com.example.omstugradebook.fragments.TimetableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_grade:
                    loadFragment(GradeFragment.newInstance());
                    return true;
                case R.id.bottom_navigation_item_timetable:
                    loadFragment(TimetableFragment.newInstance());
                    return true;
                case R.id.bottom_navigation_item_profile:
                    loadFragment(AccountFragment.newInstance());
                    return true;
            }
            return false;
        }
    };


    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout_content, fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(GradeFragment.newInstance());// начальная инициализация, чтобы не было пустого экрана
    }

}