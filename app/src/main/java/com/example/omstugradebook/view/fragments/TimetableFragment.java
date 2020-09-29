package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.ScheduleDaoImpl;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.adapter.ScheduleRVAdapter;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleContentHolderConverter;
import com.example.omstugradebook.service.TimetableService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TimetableFragment extends Fragment implements Updatable {
    private Calendar start;
    private Calendar finish;
    private RecyclerView recyclerView;
    private ScheduleRVAdapter adapter = new ScheduleRVAdapter();
    private UserDao userDao;
    private String groupString = "";
    private ScheduleDao scheduleDao;
    private TextView information;


    public TimetableFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (start == null) {
            setNewCalendar(Calendar.getInstance());
        } else {
            setNewCalendar(start);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Расписание");
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        boolean dbIsEmpty = loadScheduleFromDB();
        setInformationTextView(view, dbIsEmpty);
        return view;
    }

    private void setInformationTextView(View view, boolean dbIsEmpty) {
        userDao = new UserDaoImpl();
        information = view.findViewById(R.id.timetable_information);
        if (dbIsEmpty && userDao.getActiveUser(getContext()) == null) {
            information.setText("Вы можете использовать расписание без авторизации. Для этого просто нажмите на лупу!");
        } else {
            information.setText("");
        }
    }

    private boolean loadScheduleFromDB() {
        scheduleDao = new ScheduleDaoImpl();
        List<Schedule> schedules = scheduleDao.readAllSchedule(getContext());
        adapter.setScheduleList(new ScheduleContentHolderConverter(schedules));
        return schedules.isEmpty();
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
        sendRequest();
    }

    public void setGroupString(String groupString) {
        if (!this.groupString.equals(groupString)) {
            this.groupString = groupString;
            sendRequest();
        }
    }


    private void sendRequest() {
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
    }

    @Override
    public void update() {
        if (!userDao.getActiveUser(getContext()).getStudent().getSpeciality().equals(groupString)) {
            groupString = userDao.getActiveUser(getContext()).getStudent().getSpeciality();
            sendRequest();
        }
    }


    class OmSTUSender extends AsyncTask<String, String, String> {
        private List<Schedule> schedules;


        @Override
        protected String doInBackground(String... strings) {
            userDao = new UserDaoImpl();
            TimetableService timetableService = new TimetableService();
            String group;
            if (groupString.isEmpty()) {
                User user = userDao.getActiveUser(getContext());
                if (user == null) {
                    return null;
                }
                group = user.getStudent().getSpeciality().trim();
            } else {
                group = groupString.trim();
            }
            schedules = timetableService.getTimetable(group, start, finish, 1);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (schedules != null) {
                adapter.setScheduleList(new ScheduleContentHolderConverter(schedules));
                scheduleDao = new ScheduleDaoImpl();
                scheduleDao.insertAllSchedule(schedules, getContext());
                information.setText("");
                adapter.notifyDataSetChanged();
            }
        }
    }
}
