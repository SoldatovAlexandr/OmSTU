package com.example.omstugradebook.view.activity.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.model.grade.User;

public class MainViewModel extends ViewModel {

    public boolean checkAuthActiveUser() {
        User user = DataBaseManager.getUserDao().getUser();
        return user != null;
    }
}
