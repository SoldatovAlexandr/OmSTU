package com.example.omstugradebook.view.activity.viewmodel;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.activity.model.ContactWorkTasksModel;

import java.util.concurrent.CompletableFuture;

public class ContactWorkTasksViewModel extends ViewModel {
    private final MutableLiveData<ContactWorkTasksModel> contactWorkModelLiveData;

    private final MutableLiveData<String> fileStatusLiveData;

    public ContactWorkTasksViewModel() {
        contactWorkModelLiveData = new MutableLiveData<>();

        fileStatusLiveData = new MutableLiveData<>();
    }

    public LiveData<ContactWorkTasksModel> getContactWorkModelLiveData() {
        return contactWorkModelLiveData;
    }

    public LiveData<String> getFileStatusLiveData() {
        return fileStatusLiveData;
    }

    public void startDownloading(ContactWorksTask task, Context context) {
        String fileName = task.getFile().trim().replaceAll(" ", "_");

        fileStatusLiveData.postValue(new ContactWorkService()
                .downloadFile(task.getLink(), fileName, context));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendRequestToGetContactWorkTasks(String path) {
        CompletableFuture
                .supplyAsync(() -> new ContactWorkService().getTaskContactWork(path))
                .thenAccept(tasks -> {
                    if (tasks != null) {
                        contactWorkModelLiveData.postValue(new ContactWorkTasksModel(tasks));
                    }
                });
    }
}
