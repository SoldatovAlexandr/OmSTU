package com.example.omstugradebook.view.activity.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.ScheduleTypeAutoComponentAdapter;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.view.activity.CalendarProvider;
import com.example.omstugradebook.view.activity.viewmodel.MainViewModel;
import com.example.omstugradebook.view.fragments.view.AccountFragment;
import com.example.omstugradebook.view.fragments.view.ContactWorkFragment;
import com.example.omstugradebook.view.fragments.view.GradeFragment;
import com.example.omstugradebook.view.fragments.view.TimetableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior<LinearLayout> calendarBottomSheetBehavior;

    private static BottomNavigationView navigation;
    private CalendarProvider calendarProvider;
    private AutoCompleteTextView autoCompleteTextView;
    private Calendar calendar = Calendar.getInstance();
    private static final String GROUP = "group";
    private ScheduleOwner scheduleOwner = new ScheduleOwner(351, "", GROUP);

    private final View.OnClickListener fabListener = v ->
            calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            if (item.getItemId() == navigation.getSelectedItemId()) {
                return false;
            }
            calendarProvider = null;
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_grade:
                    loadFragment(new GradeFragment());
                    return true;
                case R.id.bottom_navigation_item_timetable:
                    loadTimetableFragment(new TimetableFragment(fabListener));
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
        loadTimetableFragment(new TimetableFragment(fabListener));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_content, fragment).commit();
    }

    private void initCalendarBottomShit(MainViewModel mainViewModel) {
        LinearLayout calendarLLBottomSheet = findViewById(R.id.calendar_bottom_sheet);
        calendarBottomSheetBehavior = BottomSheetBehavior.from(calendarLLBottomSheet);
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        autoCompleteTextView = findViewById(R.id.actv);
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setAdapter(new ScheduleTypeAutoComponentAdapter(this));
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            scheduleOwner = (ScheduleOwner) parent.getItemAtPosition(position);
            autoCompleteTextView.setText(scheduleOwner.getName());
            sendRequest(String.valueOf(scheduleOwner.getId()), scheduleOwner.getType());
        });

        mainViewModel.getUserGroupLiveData().observe(this, this::setUserGroup);
        mainViewModel.getUserGroup();

        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar = new GregorianCalendar(year, month, dayOfMonth);
            sendRequest(String.valueOf(scheduleOwner.getId()), scheduleOwner.getType());
        });
    }

    private void setUserGroup(String param) {
        autoCompleteTextView.setText(param);
    }

    private void sendRequest(String id, String type) {
        calendarProvider.sendRequest(calendar, id, type);
        calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    private void initNavigationMenu() {
        navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View buttonCalendar = findViewById(R.id.bottom_navigation_item_timetable);
        buttonCalendar.setOnLongClickListener(v -> {
            if (navigation.getSelectedItemId() == R.id.bottom_navigation_item_timetable) {
                calendarBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return true;
            }
            return false;
        });
    }

    private void loadTimetableFragment(TimetableFragment timetableFragment) {
        calendarProvider = timetableFragment;
        loadFragment(timetableFragment);
    }
}