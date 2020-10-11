package com.example.omstugradebook.view.activity.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.view.activity.CalendarProvider;
import com.example.omstugradebook.view.activity.viewmodel.MainViewModel;
import com.example.omstugradebook.view.fragments.view.AccountFragment;
import com.example.omstugradebook.view.fragments.view.ContactWorkFragment;
import com.example.omstugradebook.view.fragments.view.GradeFragment;
import com.example.omstugradebook.view.fragments.view.TimetableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior<LinearLayout> calendarBottomSheetBehavior;

    private static BottomNavigationView navigation;
    private EditText searchEditText;
    private FloatingActionButton fab;
    private CalendarProvider calendarProvider;
    private Calendar calendar = Calendar.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            fab.hide();
            calendarProvider = null;
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_grade:
                    loadFragment(new GradeFragment());
                    return true;
                case R.id.bottom_navigation_item_timetable:
                    loadTimetableFragment(new TimetableFragment());
                    return true;
                case R.id.bottom_navigation_item_profile:
                    loadFragment(new AccountFragment());
                    return true;
                case R.id.bottom_navigation_item_contactwork:
                    loadFragment(new ContactWorkFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initNavigationMenu();
        initCalendarBottomShit(mainViewModel);
        initFloatingActionBar();
        loadTimetableFragment(new TimetableFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_content, fragment).commit();
    }

    private void initCalendarBottomShit(MainViewModel mainViewModel) {
        LinearLayout calendarLLBottomSheet = findViewById(R.id.calendar_bottom_sheet);
        calendarBottomSheetBehavior = BottomSheetBehavior.from(calendarLLBottomSheet);
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        searchEditText = findViewById(R.id.search_edit_text);
        mainViewModel.getUserGroupLiveData().observe(this, this::setUserGroup);
        mainViewModel.getUserGroup();
        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar = new GregorianCalendar(year, month, dayOfMonth);
            sendRequest();
        });
        ImageButton searchButton = findViewById(R.id.search_button_calendar);
        searchButton.setOnClickListener(v -> sendRequest());
    }

    private void setUserGroup(String group) {
        searchEditText.setText(group);
    }

    private void sendRequest() {
        String selectString = searchEditText.getText().toString();
        calendarProvider.sendRequest(calendar, selectString);
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        fab.show();
    }

    private void initFloatingActionBar() {
        fab = findViewById(R.id.fab);
        fab.setBackgroundColor(Color.BLUE);
        fab.hide();
        fab.setOnClickListener(view -> {
            if (navigation.getSelectedItemId() == R.id.bottom_navigation_item_timetable) {
                calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab.hide();
            }
        });
    }

    private void initNavigationMenu() {
        navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View buttonCalendar = findViewById(R.id.bottom_navigation_item_timetable);
        buttonCalendar.setOnLongClickListener(v -> {
            if (navigation.getSelectedItemId() == R.id.bottom_navigation_item_timetable) {
                calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab.hide();
                return true;
            }
            return false;
        });
    }

    private void loadTimetableFragment(TimetableFragment timetableFragment) {
        calendarProvider = timetableFragment;
        loadFragment(timetableFragment);
        fab.show();
    }
}