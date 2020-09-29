package com.example.omstugradebook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omstugradebook.ConnectionDetector;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.SubjectDaoImpl;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.view.fragments.AccountFragment;
import com.example.omstugradebook.view.fragments.ContactWorkFragment;
import com.example.omstugradebook.view.fragments.GradeFragment;
import com.example.omstugradebook.view.fragments.TimetableFragment;
import com.example.omstugradebook.view.fragments.Updatable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener, CalendarView.OnDateChangeListener {

    private AccountFragment accountFragment = new AccountFragment();
    private GradeFragment gradeFragment = new GradeFragment();
    private TimetableFragment timetableFragment = new TimetableFragment();
    private ContactWorkFragment contactWorkFragment = new ContactWorkFragment();
    private View buttonProfile;
    private View buttonCalendar;
    private LinearLayout llAccounts;
    private UserDao userDao = new UserDaoImpl();
    private LinearLayout userLLBottomSheet;
    private BottomSheetBehavior userBottomSheetBehavior;
    private LinearLayout calendarLLBottomSheet;
    private BottomSheetBehavior calendarBottomSheetBehavior;
    private Map<String, Button> userButtons;
    private User activeUser;
    private static BottomNavigationView navigation;
    private CalendarView calendarView;
    private EditText searchEditText;
    private ImageButton searchButton;
    private FloatingActionButton fab;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            fab.hide();
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_grade:
                    loadFragment(gradeFragment);
                    return true;
                case R.id.bottom_navigation_item_timetable:
                    loadFragment(timetableFragment);
                    fab.show();
                    return true;
                case R.id.bottom_navigation_item_profile:
                    loadFragment(accountFragment);
                    return true;
                case R.id.bottom_navigation_item_contactwork:
                    loadFragment(contactWorkFragment);
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
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        timetableFragment.setNewCalendar(new GregorianCalendar(year, month, dayOfMonth));
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        fab.show();
    }

    private void setCalendarBottomShit() {
        calendarLLBottomSheet = findViewById(R.id.calendar_bottom_sheet);
        calendarBottomSheetBehavior = BottomSheetBehavior.from(calendarLLBottomSheet);
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        searchEditText = findViewById(R.id.search_edit_text);
        if (activeUser != null && activeUser.getStudent().getSpeciality() != null) {
            searchEditText.setText(activeUser.getStudent().getSpeciality());
        }
        calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(this);
        searchButton = findViewById(R.id.search_button_calendar);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectString = searchEditText.getText().toString();
                timetableFragment.setGroupString(selectString);
                fab.show();
                calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }


    private void setUserLLBottomSheet() {
        userLLBottomSheet = findViewById(R.id.bottom_sheet);
        userBottomSheetBehavior = BottomSheetBehavior.from(userLLBottomSheet);
        userBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        llAccounts = findViewById(R.id.accounts_change_layout);
        userButtons = new HashMap<>();
        if (activeUser == null && userDao.readAllUsers(this).isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeUser = userDao.getActiveUser(this);
        ConnectionDetector connectionDetector = new ConnectionDetector(getContext());
        if (connectionDetector.ConnectingToInternet() && userDao.getActiveUser(this) != null) {
            OmSTUSender omSTUSender = new OmSTUSender();
            omSTUSender.execute();
        }
        setNavigationMenu();
        loadFragment(GradeFragment.getInstance());
        setCalendarBottomShit();
        setUserLLBottomSheet();
        setFloatingActionBar();
    }

    private void setFloatingActionBar() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.BLUE);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigation.getSelectedItemId() == R.id.bottom_navigation_item_timetable) {
                    calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    fab.hide();
                }
            }
        });
    }

    private void setNavigationMenu() {
        navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        buttonProfile = findViewById(R.id.bottom_navigation_item_profile);
        buttonProfile.setOnLongClickListener(this);
        buttonCalendar = findViewById(R.id.bottom_navigation_item_timetable);
        buttonCalendar.setOnLongClickListener(this);
    }

    public static BottomNavigationView getNavigation() {
        return navigation;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.bottom_navigation_item_timetable) {
            if (navigation.getSelectedItemId() == R.id.bottom_navigation_item_timetable) {
                calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab.hide();
            }
        } else {
            llAccounts.removeAllViews();
            userButtons.clear();
            for (User user : userDao.readAllUsers(this)) {
                Button userButton = new Button(this);
                userButtons.put(user.getLogin(), userButton);
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
            userBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        return true;
    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        try {
            String loginOnView = ((Button) v).getText().toString();
            if (!userDao.getActiveUser(this).getLogin().equals(loginOnView)) {
                userDao.changeActiveUser(userDao.getUserByLogin(loginOnView, this), this);
                userBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                updateCurrentFragment();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCurrentFragment() {
        getCurrentUpdatableFragment().update();
    }


    private Updatable getCurrentUpdatableFragment() {
        switch (navigation.getSelectedItemId()) {
            case R.id.bottom_navigation_item_profile:
                return accountFragment;
            case R.id.bottom_navigation_item_grade:
                return gradeFragment;
            case R.id.bottom_navigation_item_timetable:
                return timetableFragment;
            case R.id.bottom_navigation_item_contactwork:
                return contactWorkFragment;
        }
        return contactWorkFragment;
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            AuthService auth = new AuthService();
            UserDao userDao = new UserDaoImpl();
            SubjectDao subjectDao = new SubjectDaoImpl();
            if (userDao.getActiveUser(getContext()) == null) {
                return null;
            }
            GradeBook gradeBook = auth.getGradeBook(getContext());
            if (gradeBook != null) {
                List<Subject> subjects = new ArrayList<>();
                for (Term term : gradeBook.getTerms()) {
                    subjects.addAll(term.getSubjects());
                }
                for (Subject subject : subjects) {
                    subject.setUserId((int) activeUser.getId());
                }
                if (!subjectDao.equalsSubjects(subjects, getContext())) {
                    subjectDao.removeAllSubjects(getContext());
                    subjectDao.insertAllSubjects(subjects, getContext());
                }
            }
            return null;
        }
    }

}