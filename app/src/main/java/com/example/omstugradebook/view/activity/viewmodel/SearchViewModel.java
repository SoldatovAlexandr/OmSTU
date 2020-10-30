package com.example.omstugradebook.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.view.state.model.DataCashRepository;
import com.example.omstugradebook.view.state.model.SearchStateModel;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<String> userGroupLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changeLikeImageLiveData = new MutableLiveData<>();


    public LiveData<String> getUserGroupLiveData() {
        return userGroupLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    public LiveData<Boolean> getChangeLikeImageLiveData() {
        return changeLikeImageLiveData;
    }

    public void getUserGroup() {
        userGroupLiveData.postValue(getCash().getScheduleOwner().getName());

        List<ScheduleOwner> favoriteSchedules = getAllFavoriteSchedule();
        if (!favoriteSchedules.isEmpty()) {
            userGroupLiveData.postValue(favoriteSchedules.get(0).getName());
        }
    }

    public List<ScheduleOwner> getAllFavoriteSchedule() {
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        return scheduleDao.readFavoriteSchedule();
    }

    public void changeFavoriteSchedule(ScheduleOwner scheduleOwner, boolean like) {
        if (scheduleOwner == null) {
            errorLiveData.postValue("Выберите рассписание,\n чтобы его сохранить!");
            if (like) {
                changeLikeImageLiveData.postValue(false);
            }
        } else {
            ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
            if (like) {
                scheduleDao.removeFavoriteSchedule(scheduleOwner);
            } else {
                scheduleDao.insertFavoriteSchedule(scheduleOwner);
            }
            changeLikeImageLiveData.postValue(!like);
        }
    }

    public SearchStateModel getCash() {
        return DataCashRepository.getInstance().getSearchStateModel();
    }

    public void saveSearchState(SearchStateModel searchStateModel) {
        DataCashRepository.getInstance().saveSearchStateModel(searchStateModel);
    }
}
