package com.example.omstugradebook.presentation.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.AppException;
import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.grade.User;
import com.example.omstugradebook.domain.interactor.LoginInteractor;

import java.util.concurrent.CompletableFuture;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> errorLiveData = new MutableLiveData<>();

    private final LoginInteractor loginInteractor = new LoginInteractor();


    public LoginViewModel() {
        App.getComponent().injectLoginViewModel(this);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Integer> getErrorLiveData() {
        return errorLiveData;
    }


    public void signIn(String login, String password) {
        CompletableFuture.runAsync(() -> {
            try {
                User user = loginInteractor.signIn(login, password);

                userLiveData.postValue(user);
            } catch (AppException e) {
                errorLiveData.postValue(R.string.serverException);
            }
        });
    }
}
