package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.Schedule;
import com.example.omstugradebook.recyclerview.adapter.ScheduleRVAdapter;
import com.example.omstugradebook.service.TimetableService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TimetableFragment extends Fragment {
    private Calendar start;
    private Calendar finish;
    private RecyclerView recyclerView;
    private ScheduleRVAdapter adapter = new ScheduleRVAdapter(new ArrayList<Schedule>());
    private UserDao userDao;

    public TimetableFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (start == null) {
            setNewCalendar(Calendar.getInstance());
        } else {
            setNewCalendar(start);
        }
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setNewCalendar(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek == -1) {
            dayOfWeek = 6;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        start = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        finish = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
    }


    class OmSTUSender extends AsyncTask<String, String, String> {
        private List<Schedule> schedules;


        @Override
        protected String doInBackground(String... strings) {
            userDao = new UserDaoImpl(getContext());
            TimetableService timetableService = new TimetableService();
            String group=userDao.getActiveUser().getStudent().getSpeciality().trim();
            schedules = timetableService.getTimetable(group, start, finish, 1);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (schedules != null) {
                adapter.setScheduleList(schedules);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
