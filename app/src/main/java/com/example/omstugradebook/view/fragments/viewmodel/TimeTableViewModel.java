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
import com.example.omstugradebook.service.TimetableService;
import com.example.omstugradebook.view.fragments.model.TimetableModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TimeTableViewModel extends ViewModel {

    private MutableLiveData<TimetableModel> timetableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> infoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();


    public LiveData<TimetableModel> getTimetablesLiveData() {
        return timetableLiveData;
    }

    public LiveData<String> getInfoLiveData() {
        return infoLiveData;
    }

    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public void getSchedules(Calendar calendar) {
        User user = DataBaseManager.getUserDao().getActiveUser();
        if (user != null) {
            List<Schedule> schedules = DataBaseManager.getScheduleDao().readScheduleByUserId(user.getId());
            timetableLiveData.postValue(new TimetableModel(schedules));
            titleLiveData.postValue("Расписание");
        }
        Calendar start = getStartCalendar(calendar);
        Calendar finish = getFinishCalendar(start);
        new OmSTUSender().execute(getDateString(start), getDateString(finish));
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

    private boolean updateDataBase(List<Schedule> schedules) {
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        List<Schedule> schedulesFromDB = scheduleDao.readScheduleByUserId(DataBaseManager
                .getUserDao().getActiveUser().getId());
        if (!schedules.equals(schedulesFromDB)) {
            scheduleDao.removeSchedulesById(DataBaseManager.getUserDao().getActiveUser().getId());
            scheduleDao.insertAllSchedule(schedules);
            return true;
        }
        return false;
    }

    class OmSTUSender extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String start = strings[0];
            String finish = strings[1];
            TimetableService timetableService = new TimetableService();
            User user = DataBaseManager.getUserDao().getActiveUser();
            if (user == null) {
                infoLiveData.postValue("Вы можете использовать расписание без авторизации. Для этого просто нажмите на лупу!");
            } else {
                String group = user.getStudent().getSpeciality().trim();
                List<Schedule> schedules = timetableService.getTimetable(group, start, finish, 1);
                if (updateDataBase(schedules)) {
                    TimetableModel timetableModel = new TimetableModel(schedules);
                    timetableLiveData.postValue(timetableModel);
                }
            }
            return null;
        }

    }
}
