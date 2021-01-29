package com.example.omstugradebook.presentation.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.dao.SearchScheduleDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.schedule.ScheduleOwner;
import com.example.omstugradebook.data.model.schedule.SearchSchedule;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<String> userGroupLiveData;

    private final MutableLiveData<String> errorLiveData;

    private final MutableLiveData<Boolean> changeLikeImageLiveData;

    private final MutableLiveData<SearchSchedule> searchScheduleLiveData;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    @Inject
    SearchScheduleDao searchScheduleDao;

    @Inject
    UserDao userDao;

    public SearchViewModel() {
        App.getComponent().injectSearchViewModel(this);

        userGroupLiveData = new MutableLiveData<>();

        errorLiveData = new MutableLiveData<>();

        changeLikeImageLiveData = new MutableLiveData<>();

        searchScheduleLiveData = new MutableLiveData<>();
    }

    public void getUserGroup() {
        CompletableFuture.runAsync(() -> {
            userGroupLiveData.postValue(searchScheduleDao.get().getName());
        });
    }

    public void getScheduleOwners(String name) {
        CompletableFuture.runAsync(() -> {
            boolean result = scheduleOwnerDao.checkFavoriteSchedule(name);

            changeLikeImageLiveData.postValue(result);
        });
    }

    public void changeFavoriteSchedule(ScheduleOwner scheduleOwner, boolean like) {
        CompletableFuture.runAsync(() -> {
            if (scheduleOwner == null) {
                errorLiveData.postValue("Выберите рассписание,\n чтобы его сохранить!");

                if (like) {
                    changeLikeImageLiveData.postValue(false);
                }
            } else {
                if (like) {
                    scheduleOwnerDao.delete(scheduleOwner);
                } else {
                    scheduleOwnerDao.insertAll(scheduleOwner);
                }
                changeLikeImageLiveData.postValue(!like);
            }
        });
    }

    public void getSearchSchedule() {
        CompletableFuture.runAsync(() -> {
            SearchSchedule searchSchedule = searchScheduleDao.get();

            searchScheduleLiveData.postValue(searchSchedule);
        });
    }

    public void saveSearchSchedule(SearchSchedule searchSchedule) {
        CompletableFuture.runAsync(() -> searchScheduleDao.update(searchSchedule));
    }

    public LiveData<String> getUserGroupLiveData() {
        return userGroupLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getChangeLikeImageLiveData() {
        return changeLikeImageLiveData;
    }

    public LiveData<SearchSchedule> getSearchScheduleLiveData() {
        return searchScheduleLiveData;
    }
}
