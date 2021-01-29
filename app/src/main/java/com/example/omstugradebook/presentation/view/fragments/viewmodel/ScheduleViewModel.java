package com.example.omstugradebook.presentation.view.fragments.viewmodel;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.ScheduleDao;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.dao.SearchScheduleDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.grade.User;
import com.example.omstugradebook.data.model.schedule.Schedule;
import com.example.omstugradebook.data.model.schedule.ScheduleOwner;
import com.example.omstugradebook.data.model.schedule.SearchSchedule;
import com.example.omstugradebook.domain.connector.ScheduleService;
import com.example.omstugradebook.presentation.view.fragments.model.TimetableModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class ScheduleViewModel extends ViewModel {
    private final MutableLiveData<TimetableModel> timetableLiveData;

    private final MutableLiveData<Integer> infoLiveData;

    private final MutableLiveData<String> titleLiveData;

    @Inject
    UserDao userDao;

    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    @Inject
    SearchScheduleDao searchScheduleDao;

    public ScheduleViewModel() {
        App.getComponent().injectScheduleViewModel(this);

        timetableLiveData = new MutableLiveData<>();

        infoLiveData = new MutableLiveData<>();

        titleLiveData = new MutableLiveData<>();
    }

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
        CompletableFuture
                .runAsync(() -> {
                    SearchSchedule searchSchedule = searchScheduleDao.get();

                    Calendar calendar;

                    ScheduleOwner scheduleOwner;

                    if (searchSchedule == null) {
                        calendar = Calendar.getInstance();

                        User user = userDao.get();

                        String speciality = user.getStudent().getSpeciality();

                        scheduleOwner = scheduleOwnerDao.get(speciality);

                        saveSearchSchedule(calendar, scheduleOwner);
                    } else {
                        scheduleOwner = new ScheduleOwner(
                                (int) searchSchedule.getId(),
                                searchSchedule.getName(),
                                searchSchedule.getType());

                        calendar = searchSchedule.getCalendar();
                    }
                    String id = String.valueOf(scheduleOwner.getId());

                    getSchedules(calendar, id, scheduleOwner.getType(), scheduleOwner.getName());
                });
    }

    private void saveSearchSchedule(Calendar calendar, ScheduleOwner scheduleOwner) {
        SearchSchedule searchSchedule = new SearchSchedule(
                scheduleOwner.getId(),
                scheduleOwner.getName(),
                scheduleOwner.getType(),
                calendar
        );

        searchScheduleDao.update(searchSchedule);
    }


    public void getSchedules(Calendar calendar, String id, String type, String param) {
        CompletableFuture
                .runAsync(() -> titleLiveData.postValue("Расписание"))
                .thenRun(() -> {
                    long favoriteId = getFavoriteId(param);

                    Calendar start = getStartCalendar(calendar);

                    Calendar finish = getFinishCalendar(start);

                    sendRequest(getDateString(start), getDateString(finish), id, type, String.valueOf(favoriteId), param);
                });
    }

    private void sendRequest(String start, String finish, String id, String type, String favoriteId,
                             String param) {

        ScheduleService scheduleService = new ScheduleService();

        User user = userDao.get();

        if (user != null) {
            sendScheduleLiveData(scheduleService, id, start, finish, type, favoriteId, param);
        }
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

    private void updateDataBase(List<Schedule> schedules, String favoriteId) {
        long id = Long.parseLong(favoriteId);

        scheduleDao.deleteByFavoriteId(id);

        for (Schedule schedule : schedules) {
            schedule.setFavoriteId(id);
        }

        scheduleDao.insert(schedules);
    }


    private void sendScheduleLiveData(ScheduleService scheduleService, String id, String start,
                                      String finish, String type, String favoriteId, String param) {
        List<Schedule> schedules = scheduleService.getTimetable(id, start, finish, type, 1);

        if (!schedules.isEmpty()) {
            if (scheduleOwnerDao.checkFavoriteSchedule(param)) {
                updateDataBase(schedules, favoriteId);
            }

            TimetableModel timetableModel = new TimetableModel(schedules);

            timetableLiveData.postValue(timetableModel);
        }
    }

    private long getFavoriteId(String param) {
        int favoriteId = 0;

        if (scheduleOwnerDao.checkFavoriteSchedule(param)) {
            ScheduleOwner scheduleOwner = scheduleOwnerDao.get(param);

            favoriteId = scheduleOwner.getId();

            List<Schedule> schedules = scheduleDao.getByFavoriteId(favoriteId);

            timetableLiveData.postValue(new TimetableModel(schedules));
        }
        return favoriteId;
    }
}
