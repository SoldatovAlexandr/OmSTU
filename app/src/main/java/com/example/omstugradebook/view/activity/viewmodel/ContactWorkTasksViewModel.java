package com.example.omstugradebook.view.activity.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.activity.model.ContactWorkTasksModel;

import java.util.List;

public class ContactWorkTasksViewModel extends ViewModel {
    private MutableLiveData<ContactWorkTasksModel> contactWorkModelLiveData = new MutableLiveData<>();

    public LiveData<ContactWorkTasksModel> getContactWorkModelLiveData() {
        return contactWorkModelLiveData;
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
