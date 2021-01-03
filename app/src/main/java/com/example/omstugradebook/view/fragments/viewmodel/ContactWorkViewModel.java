package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ContactWorkDao;
import com.example.omstugradebook.dao.UserDao;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.fragments.model.ContactWorkModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.Module;

@Module
public class ContactWorkViewModel extends ViewModel {
    private final MutableLiveData<ContactWorkModel> contactWorkModelLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    ContactWorkDao contactWorkDao;

    @Inject
    UserDao userDao;

    public ContactWorkViewModel() {
        App.getComponent().injectContactWorkViewModel(this);
    }


    public MutableLiveData<ContactWorkModel> getContactWorkLiveData() {
        return contactWorkModelLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getContactWorks() {
        CompletableFuture.runAsync(() -> {
            User activeUser = userDao.get();

            if (activeUser != null) {
                List<ContactWork> contactWorksFromDB = contactWorkDao.getAll();

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
                                contactWorkDao.deleteAll();
                                contactWorkDao.insertAll(contactWorks);
                            }
                        });

            } else {
                errorLiveData.postValue("Чтобы пользоваться контактной работой, войдите в аккаунт.");
            }
        });
    }
}
