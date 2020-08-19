package com.example.omstugradebook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.ConnectionDetector;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.SubjectTable;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.fragments.AccountFragment;
import com.example.omstugradebook.fragments.GradeFragment;
import com.example.omstugradebook.fragments.TimetableFragment;
import com.example.omstugradebook.model.GradeBook;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.model.Term;
import com.example.omstugradebook.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {

    private View buttonProfile;
    private Button buttonAddNewUser;
    private LinearLayout llAccounts;
    UserTable userTable = new UserTable(this);
    private LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private Map<String, Button> userButtons;
    private User activeUser;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_grade:
                    loadFragment(GradeFragment.newInstance());
                    return true;
                case R.id.bottom_navigation_item_timetable:
                    loadFragment(TimetableFragment.getInstance());
                    return true;
                case R.id.bottom_navigation_item_profile:
                    loadFragment(AccountFragment.getInstance());
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
        ConnectionDetector connectionDetector = new ConnectionDetector(getContext());
        if (connectionDetector.ConnectingToInternet() && userTable.getActiveUser() != null) {
            OmSTUSender omSTUSender = new OmSTUSender();
            omSTUSender.execute();
        }
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        buttonProfile = findViewById(R.id.bottom_navigation_item_profile);
        buttonProfile.setOnLongClickListener(this);
        loadFragment(GradeFragment.newInstance());
        llBottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        llAccounts = findViewById(R.id.accounts_change_layout);
        buttonAddNewUser = findViewById(R.id.add_new_user_button);
        buttonAddNewUser.setOnClickListener(this);
        userButtons = new HashMap<>();
        activeUser = userTable.getActiveUser();
        if (activeUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        llAccounts.removeAllViews();
        userButtons.clear();
        for (User user : userTable.readAllUsers()) {
            Button userButton = new Button(this);
            userButtons.put(userButton.getText().toString(), userButton);
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

    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_new_user_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        for (Button button : userButtons.values()) {
            if (button.getId() == v.getId()) {
                String login = button.getText().toString();
                if (!userTable.getActiveUser().getLogin().equals(login)) {
                    userTable.changeActiveUser(userTable.getUserByLogin(login));
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    return;
                }
            }
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String login = data.getStringExtra("login");
        userButtons.remove(login);
        activeUser = userTable.getActiveUser();
        AccountFragment.getInstance().update();
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            Auth auth = new Auth();
            UserTable userTable = new UserTable(getContext());
            SubjectTable subjectTable = new SubjectTable(getContext());
            if (userTable.getActiveUser() == null) {
                return null;
            }
            GradeBook gradeBook = auth.getGradeBook(getContext());
            if (gradeBook != null) {
                List<Subject> subjects = new ArrayList<>();
                for (Term term : gradeBook.getTerms()) {
                    subjects.addAll(term.getSubjects());
                }
                User user = userTable.getActiveUser();
                user.setStudent(gradeBook.getStudent());
                userTable.update(user);
                if (!subjectTable.equalsSubjects(subjects)) {
                    subjectTable.removeAllSubjects();
                    subjectTable.insertAllSubjects(subjects);
                }
            }
            return null;
        }
    }

}