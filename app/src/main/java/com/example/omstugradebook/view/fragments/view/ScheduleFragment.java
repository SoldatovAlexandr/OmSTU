package com.example.omstugradebook.view.fragments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.adapter.ScheduleRVAdapter;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleContentHolderConverter;
import com.example.omstugradebook.view.activity.view.SearchActivity;
import com.example.omstugradebook.view.fragments.Updatable;
import com.example.omstugradebook.view.fragments.viewmodel.ScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleFragment extends Fragment implements Updatable {

    private final ScheduleRVAdapter adapter = new ScheduleRVAdapter();
    private FloatingActionButton fab;
    private ScheduleViewModel timeTableViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        initFloatingActionBar(view);
        initRecyclerView(view);

        timeTableViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        timeTableViewModel.getTimetablesLiveData().observe(getViewLifecycleOwner(),
                timetableModel -> update(timetableModel.getSchedules()));
        timeTableViewModel.getInfoLiveData()
                .observe(getViewLifecycleOwner(), info -> showToastMessage(getString(info)));
        timeTableViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), this::setTitle);
        timeTableViewModel.getSchedules();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        timeTableViewModel.getSchedules();
    }

    @Override
    public void update() {
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.isShown()) {
                    fab.hide();
                }
                if (dy < 0 && !fab.isShown()) {
                    fab.show();
                }
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    private void setTitle(String title) {
        requireActivity().setTitle(title);
    }

    private void update(List<Schedule> schedules) {
        adapter.setScheduleList(new ScheduleContentHolderConverter(schedules));
        adapter.notifyDataSetChanged();
    }

    private void initFloatingActionBar(View view) {
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            return;
        }
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int dayOfMonth = intent.getIntExtra("dayOfMonth", 0);
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        String id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        String param = intent.getStringExtra("param");
        timeTableViewModel.getSchedules(calendar, id, type, param);
    }
}
