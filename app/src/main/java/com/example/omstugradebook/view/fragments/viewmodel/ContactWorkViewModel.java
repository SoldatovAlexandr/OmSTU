package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.ContactWorkDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.fragments.model.ContactWorkModel;

import java.util.List;

public class ContactWorkViewModel extends ViewModel {
    private MutableLiveData<ContactWorkModel> contactWorkModelLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<ContactWorkModel> getContactWorkLiveData() {
        return contactWorkModelLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    public void getContactWorks() {
        UserDao userDao = DataBaseManager.getUserDao();
        User activeUser = userDao.getActiveUser();
        if (activeUser != null) {
            ContactWorkDao contactWorkDao = DataBaseManager.getContactWorkDao();
            List<ContactWork> contactWorks = contactWorkDao.readContactWorkByUserId(activeUser.getId());
            if (contactWorks != null) {
                contactWorkModelLiveData.postValue(new ContactWorkModel(contactWorks));
            }
            new OmSTUSender().execute(String.valueOf(activeUser.getId()));
        } else {
            errorLiveData.postValue("Чтобы пользоваться контактной работой, войдите в аккаунт.");
        }
    }

    class OmSTUSender extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            long id = Long.parseLong(strings[0]);
            ContactWorkService contactWorkService = new ContactWorkService();
            ContactWorkDao contactWorkDao = DataBaseManager.getContactWorkDao();
            List<ContactWork> contactWorksFromDB = contactWorkDao.readContactWorkByUserId(id);
            List<ContactWork> contactWorks = contactWorkService.getContactWork(id);
            if (contactWorks == null) {
                errorLiveData.postValue("Возникли проблемы на сервере");
            } else {
                if (!contactWorksFromDB.equals(contactWorks)) {
                    contactWorkModelLiveData.postValue(new ContactWorkModel(contactWorks));
                    contactWorkDao.removeAllToContactWorkById(id);
                    contactWorkDao.insertAllToContactWork(contactWorks);
                }
            }
            return null;
        }
    }
}
