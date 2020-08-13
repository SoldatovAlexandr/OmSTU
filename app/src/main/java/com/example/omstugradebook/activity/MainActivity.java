package com.example.omstugradebook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.fragments.AccountFragment;
import com.example.omstugradebook.fragments.GradeFragment;
import com.example.omstugradebook.fragments.TimetableFragment;
import com.example.omstugradebook.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {

    private View buttonProfile;
    private Button buttonAddNewUser;
    private LinearLayout llAccounts;
    UserTable userTable = new UserTable(this);
    private LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private List<Button> listUserButton;
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
        buttonProfile = findViewById(R.id.bottom_navigation_item_profile);
        buttonProfile.setOnLongClickListener(this);
        loadFragment(GradeFragment.newInstance());
        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        llAccounts = findViewById(R.id.accounts_change_layout);
        buttonAddNewUser = findViewById(R.id.add_new_user_button);
        buttonAddNewUser.setOnClickListener(this);
        listUserButton = new ArrayList<>();
        User activeUser = userTable.getActiveUser();
        if (activeUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        llAccounts.removeAllViews();
        listUserButton.clear();
        for (User user : userTable.readAllUsers()) {
            Button userButton = new Button(this);
            listUserButton.add(userButton);
            userButton.setText(user.getLogin());
            if (user.getIsActive() == 1) {
                userButton.setTextColor(Color.BLUE);
            } else {
                userButton.setTextColor(Color.BLACK);
            }
            userButton.setBackgroundColor(Color.WHITE);
            userButton.setOnClickListener(this);
            llAccounts.addView(userButton);
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_user_button:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
        for (Button button : listUserButton) {
            if (button.getId() == v.getId()) {
                String login = button.getText().toString();
                if (!userTable.getActiveUser().getLogin().equals(login)) {
                    userTable.changeActiveUser(userTable.getUserByLogin(login));
                }
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String login = data.getStringExtra("login");
        String password = data.getStringExtra("password");
        System.out.println(login + "  " + password);
    }
}