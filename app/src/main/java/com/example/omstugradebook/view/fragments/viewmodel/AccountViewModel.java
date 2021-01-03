package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ContactWorkDao;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.SubjectDao;
import com.example.omstugradebook.dao.UserDao;
import com.example.omstugradebook.model.grade.User;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class AccountViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData;

    private final MutableLiveData<String> errorLiveData;

    @Inject
    UserDao userDao;

    @Inject
    SubjectDao subjectDao;

    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ContactWorkDao contactWorkDao;

    public AccountViewModel() {
        App.getComponent().injectAccountViewModel(this);

        userLiveData = new MutableLiveData<>();

        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void logoutUser() {
        CompletableFuture.runAsync(() -> {
            User user = userDao.get();

            userDao.delete(user);

            contactWorkDao.deleteAll();

            scheduleDao.deleteAll();

            subjectDao.deleteAll();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUser() {
        CompletableFuture.runAsync(() -> userLiveData.postValue(userDao.get()));
    }
}
