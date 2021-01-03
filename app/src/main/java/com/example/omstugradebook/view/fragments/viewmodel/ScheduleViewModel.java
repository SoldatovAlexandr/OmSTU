package com.example.omstugradebook.view.fragments.viewmodel;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.dao.UserDao;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSchedules() {
        CompletableFuture.runAsync(() -> {
            List<Schedule> cash = DataCashRepository.getInstance().getScheduleStateModel().getSchedules();

            timetableLiveData.postValue(new TimetableModel(cash));

            SearchStateModel searchStateModel = DataCashRepository.getInstance().getSearchStateModel();

            Calendar calendar = searchStateModel.getCalendar();

            ScheduleOwner scheduleOwner;

            if (searchStateModel.getScheduleOwner() == null) {

                User user = userDao.get();

                String speciality = user.getStudent().getSpeciality();

                scheduleOwner = scheduleOwnerDao.get(speciality);

                DataCashRepository.getInstance().saveSearchStateModel(new SearchStateModel(
                        scheduleOwner,
                        Calendar.getInstance()));
            } else {
                scheduleOwner = searchStateModel.getScheduleOwner();
            }
            String id = String.valueOf(scheduleOwner.getId());

            getSchedules(calendar, id, scheduleOwner.getType(), scheduleOwner.getName());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSchedules(Calendar calendar, String id, String type, String param) {
        CompletableFuture.runAsync(() -> {
            titleLiveData.postValue("Расписание");

            int favoriteId = 0;

            if (scheduleOwnerDao.checkFavoriteSchedule(param)) {
                ScheduleOwner scheduleOwner = scheduleOwnerDao.get(param);

                favoriteId = scheduleOwner.getId();

                List<Schedule> schedules = scheduleDao.getByFavoriteId(favoriteId);

                timetableLiveData.postValue(new TimetableModel(schedules));

                DataCashRepository.getInstance().saveScheduleStateModel(new ScheduleStateModel(schedules));
            }

            Calendar start = getStartCalendar(calendar);

            Calendar finish = getFinishCalendar(start);

            sendRequest(getDateString(start), getDateString(finish), id, type, String.valueOf(favoriteId), param);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

            DataCashRepository.getInstance().saveScheduleStateModel(new ScheduleStateModel(schedules));
        }
    }
}
