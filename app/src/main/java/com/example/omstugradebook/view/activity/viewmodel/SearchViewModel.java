package com.example.omstugradebook.view.activity.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.view.state.model.DataCashRepository;
import com.example.omstugradebook.view.state.model.SearchStateModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<String> userGroupLiveData;

    private final MutableLiveData<String> errorLiveData;

    private final MutableLiveData<Boolean> changeLikeImageLiveData;


    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;


    public SearchViewModel() {
        App.getComponent().injectSearchViewModel(this);

        userGroupLiveData = new MutableLiveData<>();

        errorLiveData = new MutableLiveData<>();

        changeLikeImageLiveData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUserGroup() {
        CompletableFuture.runAsync(() -> {
            userGroupLiveData.postValue(getCash().getScheduleOwner().getName());

            List<ScheduleOwner> favoriteSchedules = scheduleOwnerDao.getAll();

            if (!favoriteSchedules.isEmpty()) {
                userGroupLiveData.postValue(favoriteSchedules.get(0).getName());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getScheduleOwners(String name) {
        CompletableFuture.runAsync(() -> {
            boolean result = scheduleOwnerDao.checkFavoriteSchedule(name);

            changeLikeImageLiveData.postValue(result);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    public SearchStateModel getCash() {
        return DataCashRepository.getInstance().getSearchStateModel();
    }

    public void saveSearchState(SearchStateModel searchStateModel) {
        DataCashRepository.getInstance().saveSearchStateModel(searchStateModel);
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
}
