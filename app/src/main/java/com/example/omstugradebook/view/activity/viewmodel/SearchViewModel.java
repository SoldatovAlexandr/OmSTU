package com.example.omstugradebook.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.view.activity.model.SearchStateModel;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<String> userGroupLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changeLikeImageLiveData = new MutableLiveData<>();

    private static SearchStateModel mSearchStateModel;

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
        if (mSearchStateModel != null) {
            userGroupLiveData.postValue(mSearchStateModel.getScheduleOwner().getName());
            return;
        }
        List<String> favoriteSchedules = getAllFavoriteSchedule();
        if (!favoriteSchedules.isEmpty()) {
            userGroupLiveData.postValue(favoriteSchedules.get(0));
            return;
        }
        UserDao userDao = DataBaseManager.getUserDao();
        User user = userDao.getUser();
        if (user != null && user.getStudent().getSpeciality() != null) {
            userGroupLiveData.postValue(user.getStudent().getSpeciality());
        }
    }

    public void saveSearchState(SearchStateModel searchStateModel) {
        mSearchStateModel = searchStateModel;
    }

    public SearchStateModel getSearchState() {
        return mSearchStateModel;
    }

    public List<String> getAllFavoriteSchedule() {
        ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
        return scheduleDao.readFavoriteSchedule();
    }

    public void changeFavoriteSchedule(String value, boolean like) {
        if (value.isEmpty()) {
            errorLiveData.postValue("Выберите рассписание,\n чтобы его сохранить!");
            if (like) {
                changeLikeImageLiveData.postValue(false);
            }
        } else {
            ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
            if (like) {
                scheduleDao.removeFavoriteSchedule(value);
            } else {
                scheduleDao.insertFavoriteSchedule(value);
            }
            changeLikeImageLiveData.postValue(!like);
        }
    }
}
