package com.example.omstugradebook.view.fragments.viewmodel;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.service.ScheduleService;
import com.example.omstugradebook.view.fragments.model.TimetableModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleViewModel extends ViewModel {

    private final MutableLiveData<TimetableModel> timetableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> infoLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> titleLiveData = new MutableLiveData<>();


    public LiveData<TimetableModel> getTimetablesLiveData() {
        return timetableLiveData;
    }

    public LiveData<Integer> getInfoLiveData() {
        return infoLiveData;
    }

    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public void getSchedules() {
        Calendar calendar = Calendar.getInstance();
        getSchedules(calendar, "", "group");
    }


    public void getSchedules(Calendar calendar, String id, String type) {
        User user = DataBaseManager.getUserDao().getUser();
        titleLiveData.postValue("Расписание");
        if (user != null) {
            List<Schedule> schedules = DataBaseManager.getScheduleDao().readAllSchedules();
            timetableLiveData.postValue(new TimetableModel(schedules));
        }
        Calendar start = getStartCalendar(calendar);
        Calendar finish = getFinishCalendar(start);
        new OmSTUSender().execute(getDateString(start), getDateString(finish), id, type);
    }

    private Calendar getStartCalendar(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek == -1) {
            dayOfWeek = 6;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        return new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Calendar getFinishCalendar(Calendar calendar) {
        Calendar finish = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        finish.add(Calendar.DAY_OF_MONTH, 6);
        return finish;
    }


    @SuppressLint("SimpleDateFormat")
    private String getDateString(Calendar calendar) {
        return new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime());
    }

    private void updateDataBase(List<Schedule> schedules) {
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        List<Schedule> schedulesFromDB = scheduleDao.readAllSchedules();
        if (!schedules.equals(schedulesFromDB)) {
            scheduleDao.removeAllSchedules();
            scheduleDao.insertAllSchedule(schedules);
        }
    }

    private String getId(String group, ScheduleService scheduleService) {
        List<ScheduleOwner> scheduleOwners = scheduleService.getScheduleOwners(group);
        return !scheduleOwners.isEmpty() ? String.valueOf(scheduleOwners.get(0).getId()) : "351";
    }

    private void sendScheduleLiveData(ScheduleService scheduleService, String id, String start,
                                      String finish, String type) {
        List<Schedule> schedules = scheduleService.getTimetable(id, start, finish, type, 1);
        updateDataBase(schedules);
        TimetableModel timetableModel = new TimetableModel(schedules);
        timetableLiveData.postValue(timetableModel);
    }

    public String getParam(User user) {
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        List<String> strings = scheduleDao.readFavoriteSchedule();
        if (!strings.isEmpty()) {
            return strings.get(0);
        }
        if (user != null) {
            return user.getStudent().getSpeciality();
        }
        return "";
    }


    class OmSTUSender extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String start = strings[0];
            String finish = strings[1];
            String id = strings[2];
            String type = strings[3];
            ScheduleService scheduleService = new ScheduleService();
            User user = DataBaseManager.getUserDao().getUser();
            if (id.isEmpty()) {
                id = getId(getParam(user), scheduleService);
            }
            sendScheduleLiveData(scheduleService, id, start, finish, type);
            return null;
        }
    }
}
