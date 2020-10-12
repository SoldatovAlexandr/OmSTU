package com.example.omstugradebook.view.fragments.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.adapter.ScheduleRVAdapter;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleContentHolderConverter;
import com.example.omstugradebook.view.activity.CalendarProvider;
import com.example.omstugradebook.view.fragments.Updatable;
import com.example.omstugradebook.view.fragments.viewmodel.TimeTableViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class TimetableFragment extends Fragment implements Updatable, CalendarProvider {

    private final ScheduleRVAdapter adapter = new ScheduleRVAdapter();
    private TextView information;
    private TimeTableViewModel timeTableViewModel;
    private FloatingActionButton fab;
    private View.OnClickListener fabListener;

    public TimetableFragment(View.OnClickListener fabListener) {
        this.fabListener = fabListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        initFloatingActionBar(view);
        initRecyclerView(view);
        information = view.findViewById(R.id.timetable_information);

        timeTableViewModel = new ViewModelProvider(this).get(TimeTableViewModel.class);
        timeTableViewModel.getTimetablesLiveData().observe(getViewLifecycleOwner(),
                timetableModel -> update(timetableModel.getSchedules()));
        timeTableViewModel.getInfoLiveData()
                .observe(getViewLifecycleOwner(), info -> initInformationTextView(getString(info)));
        timeTableViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), this::setTitle);
        timeTableViewModel.getSchedules(Calendar.getInstance());

        return view;
    }

    @Override
    public void update() {
        timeTableViewModel.getSchedules(Calendar.getInstance());
    }

    @Override
    public void sendRequest(String requestType, Calendar calendar, String param) {
        timeTableViewModel.getSchedules(requestType, calendar, param);
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


    private void initInformationTextView(String informationString) {
        information.setText(informationString);
    }

    private void setTitle(String title) {
        requireActivity().setTitle(title);
    }

    private void update(List<Schedule> schedules) {
        adapter.setScheduleList(new ScheduleContentHolderConverter(schedules));
        adapter.notifyDataSetChanged();
    }

    private void initFloatingActionBar(View v) {
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(fabListener);
    }
}
