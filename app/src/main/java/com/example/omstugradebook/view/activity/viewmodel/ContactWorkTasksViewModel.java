package com.example.omstugradebook.view.activity.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.activity.model.ContactWorkTasksModel;

import java.util.List;

public class ContactWorkTasksViewModel extends ViewModel {
    private final MutableLiveData<ContactWorkTasksModel> contactWorkModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> fileStatusLiveData = new MutableLiveData<>();


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

    public void sendRequestToGetContactWorkTasks(String path) {
        new OmSTUSender().execute(path);
    }

    class OmSTUSender extends AsyncTask<String, Void, List<ContactWorksTask>> {

        @Override
        protected List<ContactWorksTask> doInBackground(String... strings) {
            String path = strings[0];
            ContactWorkService contactWorkService = new ContactWorkService();
            return contactWorkService.getTaskContactWork(path);
        }

        @Override
        protected void onPostExecute(List<ContactWorksTask> contactWorksTasks) {
            super.onPostExecute(contactWorksTasks);
            if (contactWorksTasks != null) {
                contactWorkModelLiveData.postValue(new ContactWorkTasksModel(contactWorksTasks));
            }
        }
    }
}
