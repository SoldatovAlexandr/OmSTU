package com.example.omstugradebook.view.activity.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.ScheduleTypeAutoComponentAdapter;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.model.schedule.ScheduleAutoCompleteModel;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.view.activity.viewmodel.SearchViewModel;
import com.example.omstugradebook.view.state.model.SearchStateModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

public class SearchActivity extends AppCompatActivity {
    private ScheduleOwner scheduleOwner;

    private Calendar calendar;

    private AutoCompleteTextView autoCompleteTextView;

    private SearchViewModel searchViewModel;

    private ImageView imageView;

    private boolean like;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        SearchStateModel searchStateModel = searchViewModel.getCash();

        initStartParam(searchStateModel);

        initCalendarView();

        initAutoCompleteTextView();

        initImageView();

        initSearchButton();
    }

    private void initStartParam(SearchStateModel searchStateModel) {
        calendar = searchStateModel.getCalendar();

        scheduleOwner = searchStateModel.getScheduleOwner();
    }

    private void initSearchButton() {
        Button button = findViewById(R.id.calendar_search_button);

        button.setOnClickListener(v -> sendRequest(String.valueOf(scheduleOwner.getId()),
                scheduleOwner.getType(), scheduleOwner.getName()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initImageView() {
        imageView = findViewById(R.id.favorite_image_view);

        imageView.setImageResource(R.drawable.ic_favorite_grey);

        imageView.setOnClickListener(v -> searchViewModel.changeFavoriteSchedule(scheduleOwner, like));
    }

    private void setImage(boolean bool) {
        int id = bool ? R.drawable.ic_favorite_blue : R.drawable.ic_favorite_grey;

        imageView.setImageResource(id);

        like = bool;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAutoCompleteTextView() {
        autoCompleteTextView = findViewById(R.id.actv);

        autoCompleteTextView.setThreshold(3);

        autoCompleteTextView.setAdapter(new ScheduleTypeAutoComponentAdapter(
                this,
                isNetworkConnected()));

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            ScheduleAutoCompleteModel scheduleAutoCompleteModel =
                    (ScheduleAutoCompleteModel) parent.getItemAtPosition(position);

            scheduleOwner = scheduleAutoCompleteModel.getScheduleOwner();

            String name = scheduleOwner.getName();

            autoCompleteTextView.setText(name);

            searchViewModel.getScheduleOwners(name);
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUserGroup(String name) {
        autoCompleteTextView.setText(name);

        searchViewModel.getScheduleOwners(name);
    }

    private void sendRequest(String id, String type, String param) {
        Intent intent = new Intent();

        intent.putExtra("year", calendar.get(Calendar.YEAR));

        intent.putExtra("month", calendar.get(Calendar.MONTH));

        intent.putExtra("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));

        intent.putExtra("id", id);

        intent.putExtra("type", type);

        intent.putExtra("param", param);

        setResult(RESULT_OK, intent);

        searchViewModel.saveSearchState(new SearchStateModel(scheduleOwner, calendar));

        finish();
    }

    private void showErrorMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}