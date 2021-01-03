package com.example.omstugradebook.view.activity.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.UserDao;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class StartViewModel extends ViewModel {

    @Inject
    UserDao userDao;

    private final MutableLiveData<Boolean> activeUserLiveData = new MutableLiveData<>();

    public StartViewModel() {
        App.getComponent().injectStartViewModel(this);
    }

    public MutableLiveData<Boolean> getActiveUserLiveData() {
        return activeUserLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkAuthActiveUser() {
        CompletableFuture.runAsync(() -> {
            boolean hasUser = userDao.get() != null;

            activeUserLiveData.postValue(hasUser);
        });
    }
}
