package com.example.omstugradebook.view.fragments.viewmodel;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
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
import com.example.omstugradebook.view.state.model.DataCashRepository;
import com.example.omstugradebook.view.state.model.ScheduleStateModel;
import com.example.omstugradebook.view.state.model.SearchStateModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ScheduleViewModel extends ViewModel {
    private static final DataCashRepository DATA_CASH_REPOSITORY = DataCashRepository.getInstance();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSchedules() {
        List<Schedule> cash = DATA_CASH_REPOSITORY.getScheduleStateModel().getSchedules();
        timetableLiveData.postValue(new TimetableModel(cash));
        SearchStateModel searchStateModel = DATA_CASH_REPOSITORY.getSearchStateModel();
        ScheduleOwner scheduleOwner = searchStateModel.getScheduleOwner();
        Calendar calendar = searchStateModel.getCalendar();
        getSchedules(calendar, String.valueOf(scheduleOwner.getId()), scheduleOwner.getType(),
                scheduleOwner.getName());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSchedules(Calendar calendar, String id, String type, String param) {
        titleLiveData.postValue("Расписание");
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();

        int favoriteId = 0;
        if (checkFavorite(param, scheduleDao)) {
            ScheduleOwner scheduleOwner = scheduleDao.getFavoriteScheduleByValue(param);
            favoriteId = scheduleOwner.getId();
            String fId = String.valueOf(favoriteId);
            List<Schedule> schedules = scheduleDao.readSchedulesByFavoriteId(fId);
            timetableLiveData.postValue(new TimetableModel(schedules));
            DATA_CASH_REPOSITORY.saveScheduleStateModel(new ScheduleStateModel(schedules));
        }

        Calendar start = getStartCalendar(calendar);
        Calendar finish = getFinishCalendar(start);
        sendRequest(getDateString(start), getDateString(finish), id, type, String.valueOf(favoriteId), param);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendRequest(String start, String finish, String id, String type, String favoriteId,
                             String param) {

        CompletableFuture.runAsync(() -> {
            ScheduleService scheduleService = new ScheduleService();
            User user = DataBaseManager.getUserDao().getUser();
            if (user != null) {
                sendScheduleLiveData(scheduleService, id, start, finish, type, favoriteId, param);
            }
        });

    }

    private boolean checkFavorite(String param, ScheduleDao scheduleDao) {
        for (ScheduleOwner scheduleOwner : scheduleDao.readFavoriteSchedule()) {
            if (param.equals(scheduleOwner.getName())) {
                return true;
            }
        }
        return false;
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
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        scheduleDao.removeSchedulesByFavoriteId(favoriteId);
        long id = Long.parseLong(favoriteId);
        for (Schedule schedule : schedules) {
            schedule.setFavoriteId(id);
        }
        scheduleDao.insertAllSchedule(schedules);
    }


    private void sendScheduleLiveData(ScheduleService scheduleService, String id, String start,
                                      String finish, String type, String favoriteId, String param) {
        List<Schedule> schedules = scheduleService.getTimetable(id, start, finish, type, 1);
        if (!schedules.isEmpty()) {
            if (checkFavorite(param, DataBaseManager.getScheduleDao())) {
                updateDataBase(schedules, favoriteId);
            }
            TimetableModel timetableModel = new TimetableModel(schedules);
            timetableLiveData.postValue(timetableModel);
            DATA_CASH_REPOSITORY.saveScheduleStateModel(new ScheduleStateModel(schedules));
        }
    }
}
