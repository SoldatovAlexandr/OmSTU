package com.example.omstugradebook.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.User;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> userGroupLiveData = new MutableLiveData<>();

    public LiveData<String> getUserGroupLiveData() {
        return userGroupLiveData;
    }

    public void getUserGroup() {
        UserDao userDao = DataBaseManager.getUserDao();
        User user = userDao.getActiveUser();
        if (user != null && user.getStudent().getSpeciality() != null) {
            userGroupLiveData.postValue(user.getStudent().getSpeciality());
        }
    }
}
