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

import java.util.Calendar;
import java.util.List;

public class TimetableFragment extends Fragment implements Updatable, CalendarProvider {

    private final ScheduleRVAdapter adapter = new ScheduleRVAdapter();
    private TextView information;
    private TimeTableViewModel timeTableViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        information = view.findViewById(R.id.timetable_information);

        timeTableViewModel = new ViewModelProvider(this).get(TimeTableViewModel.class);
        timeTableViewModel.getTimetablesLiveData().observe(getViewLifecycleOwner(),
                timetableModel -> update(timetableModel.getSchedules()));
        timeTableViewModel.getInfoLiveData()
                .observe(getViewLifecycleOwner(), this::setInformationTextView);
        timeTableViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), this::setTitle);
        timeTableViewModel.getSchedules(Calendar.getInstance());

        return view;
    }

    @Override
    public void update() {
        timeTableViewModel.getSchedules(Calendar.getInstance());
    }


    private void setInformationTextView(String informationString) {
        information.setText(informationString);
        //"Вы можете использовать расписание без авторизации. Для этого просто нажмите на лупу!"
    }

    private void setTitle(String title) {
        requireActivity().setTitle(title);
    }

    private void update(List<Schedule> schedules) {
        adapter.setScheduleList(new ScheduleContentHolderConverter(schedules));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void sendRequest(Calendar calendar, String param) {
        timeTableViewModel.getSchedules(calendar);
    }
}
