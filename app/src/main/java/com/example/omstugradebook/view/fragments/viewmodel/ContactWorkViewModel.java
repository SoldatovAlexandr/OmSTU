package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
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
import java.util.concurrent.CompletableFuture;

public class ContactWorkViewModel extends ViewModel {
    private final MutableLiveData<ContactWorkModel> contactWorkModelLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<ContactWorkModel> getContactWorkLiveData() {
        return contactWorkModelLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getContactWorks() {
        UserDao userDao = DataBaseManager.getUserDao();
        User activeUser = userDao.getUser();
        if (activeUser != null) {
            ContactWorkDao contactWorkDao = DataBaseManager.getContactWorkDao();
            List<ContactWork> contactWorksFromDB = contactWorkDao.readAllToContactWork();
            if (contactWorksFromDB != null) {
                contactWorkModelLiveData.postValue(new ContactWorkModel(contactWorksFromDB));
            }

            CompletableFuture
                    .supplyAsync(() -> {
                        ContactWorkService contactWorkService = new ContactWorkService();
                        return contactWorkService.getContactWork();
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        errorLiveData.postValue("Возникли проблемы на сервере");
                        return null;
                    })
                    .thenAccept(contactWorks -> {
                        if (contactWorks != null && !contactWorks.equals(contactWorksFromDB)) {
                            contactWorkModelLiveData.postValue(new ContactWorkModel(contactWorks));
                            contactWorkDao.removeAllToContactWork();
                            contactWorkDao.insertAllToContactWork(contactWorks);
                        }
                    });
        } else {
            errorLiveData.postValue("Чтобы пользоваться контактной работой, войдите в аккаунт.");
        }
    }
}
