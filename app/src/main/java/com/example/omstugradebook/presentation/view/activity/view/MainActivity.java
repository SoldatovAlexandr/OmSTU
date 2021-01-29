package com.example.omstugradebook.presentation.view.activity.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.omstugradebook.R;
import com.example.omstugradebook.presentation.view.fragments.view.AccountFragment;
import com.example.omstugradebook.presentation.view.fragments.view.ContactWorkFragment;
import com.example.omstugradebook.presentation.view.fragments.view.GradeFragment;
import com.example.omstugradebook.presentation.view.fragments.view.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    private static final String MAIN = "MAIN";

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        if (item.getItemId() == navigation.getSelectedItemId()) {
            return false;
        }
        switch (item.getItemId()) {
            case R.id.bottom_navigation_item_grade:
                loadFragment(new GradeFragment());
                return true;
            case R.id.bottom_navigation_item_timetable:
                loadFragment(new ScheduleFragment());
                return true;
            case R.id.bottom_navigation_item_profile:
                loadFragment(new AccountFragment());
                return true;
            case R.id.bottom_navigation_item_contactwork:
                loadFragment(new ContactWorkFragment());
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initNavigationMenu();

        loadFragment(new ScheduleFragment());

        navigation.setSelectedItemId(R.id.bottom_navigation_item_timetable);
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_content, fragment)
                .commit();
    }

    private void initNavigationMenu() {
        navigation = findViewById(R.id.bottom_navigation_view);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();

            intent.setType(MAIN);

            setResult(RESULT_OK, intent);

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}