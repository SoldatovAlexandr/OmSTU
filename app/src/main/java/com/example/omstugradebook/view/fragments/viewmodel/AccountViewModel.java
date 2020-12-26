package com.example.omstugradebook.view.fragments.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.model.grade.User;

public class AccountViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    public void logoutUser() {
        DataBaseManager.clearDataBase();
    }

    public void getUser() {
        userLiveData.postValue(DataBaseManager.getUserDao().getUser());
    }
}
