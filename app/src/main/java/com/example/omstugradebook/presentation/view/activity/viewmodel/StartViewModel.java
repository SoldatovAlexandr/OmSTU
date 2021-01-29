package com.example.omstugradebook.presentation.view.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.data.repository.UserRepository;
import com.example.omstugradebook.domain.interactor.StartInteractor;

import java.util.concurrent.CompletableFuture;

public class StartViewModel extends ViewModel {
    //TODO
    StartInteractor startInteractor = new StartInteractor(new UserRepository());

    private final MutableLiveData<Boolean> startLoginLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> startMainLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getStartLoginLiveData() {
        return startLoginLiveData;
    }

    public LiveData<Boolean> getStartMainLiveData() {
        return startMainLiveData;
    }

    public void checkAuthActiveUser() {
        CompletableFuture.runAsync(() -> {

            boolean hasUser = startInteractor.hasUser();

            if (hasUser) {
                startMainLiveData.postValue(true);
            } else {
                startLoginLiveData.postValue(false);
            }
        });
    }
}
