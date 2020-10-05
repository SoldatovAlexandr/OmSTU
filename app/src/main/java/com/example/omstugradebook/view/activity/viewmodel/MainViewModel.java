package com.example.omstugradebook.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.User;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> userGroupLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<String> loadLoginLiveData = new MutableLiveData<>();

    public LiveData<String> getUserGroupLiveData() {
        return userGroupLiveData;
    }

    public LiveData<String> getLoadLoginLiveData() {
        return loadLoginLiveData;
    }

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }

    public void getUserGroup() {
        UserDao userDao = DataBaseManager.getUserDao();
        User user = userDao.getActiveUser();
        if (user != null && user.getStudent().getSpeciality() != null) {
            userGroupLiveData.postValue(user.getStudent().getSpeciality());
        }
    }

    public void getUsers() {
        List<User> users = DataBaseManager.getUserDao().readAllUsers();
        if (!users.isEmpty()) {
            usersLiveData.postValue(users);
        }
    }

    public boolean changeActiveUser(String loginOnView) {
        UserDao userDao = DataBaseManager.getUserDao();
        if (!userDao.getActiveUser().getLogin().equals(loginOnView)) {
            userDao.changeActiveUser(userDao.getUserByLogin(loginOnView));
            return true;
        }
        return false;
    }

    public void checkHasAUser() {
        if (DataBaseManager.getUserDao().readAllUsers().isEmpty())
            loadLoginLiveData.postValue("");
    }
}
