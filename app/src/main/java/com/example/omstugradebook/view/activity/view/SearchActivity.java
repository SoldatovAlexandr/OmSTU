package com.example.omstugradebook.view.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.ScheduleTypeAutoComponentAdapter;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.view.activity.model.SearchStateModel;
import com.example.omstugradebook.view.activity.viewmodel.SearchViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String GROUP = "group";
    private ScheduleOwner scheduleOwner;
    private Calendar calendar;
    private AutoCompleteTextView autoCompleteTextView;
    private SearchViewModel searchViewModel;
    private ImageView imageView;
    private boolean like;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initStartParam(searchViewModel.getSearchState());
        initCalendarView();
        initAutoCompleteTextView();
        initImageView();
        initSearchButton();
    }

    private void initStartParam(SearchStateModel searchStateModel) {
        if (searchStateModel == null) {
            calendar = Calendar.getInstance();
            scheduleOwner = new ScheduleOwner(351, "", GROUP);
        } else {
            calendar = searchStateModel.getCalendar();
            scheduleOwner = searchStateModel.getScheduleOwner();
        }
    }

    private void initSearchButton() {
        Button button = findViewById(R.id.calendar_search_button);
        button.setOnClickListener(v -> sendRequest(String.valueOf(scheduleOwner.getId()),
                scheduleOwner.getType()));
    }

    private void initImageView() {
        imageView = findViewById(R.id.favorite_image_view);
        imageView.setImageResource(R.drawable.ic_favorite_grey);
        imageView.setOnClickListener(v -> searchViewModel
                .changeFavoriteSchedule(autoCompleteTextView.getText().toString(), like));
    }

    private void setImage(boolean bool) {
        int id = bool ? R.drawable.ic_favorite_blue : R.drawable.ic_favorite_grey;
        imageView.setImageResource(id);
        like = bool;
    }

    private void initCalendarView() {
        searchViewModel.getUserGroupLiveData().observe(this, this::setUserGroup);
        searchViewModel.getErrorLiveData().observe(this, this::showErrorMessage);
        searchViewModel.getChangeLikeImageLiveData().observe(this, this::setImage);

        searchViewModel.getUserGroup();

        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setDate(calendar.getTimeInMillis(), true, true);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar = new GregorianCalendar(year, month, dayOfMonth);
        });

    }


    private void initAutoCompleteTextView() {
        autoCompleteTextView = findViewById(R.id.actv);
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setAdapter(new ScheduleTypeAutoComponentAdapter(this));
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            scheduleOwner = (ScheduleOwner) parent.getItemAtPosition(position);
            String name = scheduleOwner.getName();
            autoCompleteTextView.setText(name);
            List<String> favoriteSchedule = searchViewModel.getAllFavoriteSchedule();
            setImage(checkFavoriteSchedule(name, favoriteSchedule));
        });
    }

    private boolean checkFavoriteSchedule(String name, List<String> favoriteSchedule) {
        for (String s : favoriteSchedule) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void setUserGroup(String param) {
        autoCompleteTextView.setText(param);
        setImage(checkFavoriteSchedule(param, searchViewModel.getAllFavoriteSchedule()));
    }

    private void sendRequest(String id, String type) {
        Intent intent = new Intent();
        intent.putExtra("year", calendar.get(Calendar.YEAR));
        intent.putExtra("month", calendar.get(Calendar.MONTH));
        intent.putExtra("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        setResult(RESULT_OK, intent);
        searchViewModel.saveSearchState(new SearchStateModel(scheduleOwner, calendar));
        finish();
    }

    private void showErrorMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}