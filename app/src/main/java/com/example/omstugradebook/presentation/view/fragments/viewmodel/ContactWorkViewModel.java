package com.example.omstugradebook.presentation.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.ContactWorkDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.contactwork.ContactWork;
import com.example.omstugradebook.data.parser.ContactWorkParser;
import com.example.omstugradebook.domain.connector.Connector;
import com.example.omstugradebook.presentation.view.fragments.model.ContactWorkModel;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.Module;

@Module
public class ContactWorkViewModel extends ViewModel {
    static final String REMOTE_URL = "index.php?r=remote/read";

    private final MutableLiveData<ContactWorkModel> contactWorkModelLiveData;

    private final MutableLiveData<String> errorLiveData;

    @Inject
    ContactWorkDao contactWorkDao;

    @Inject
    UserDao userDao;

    public ContactWorkViewModel() {
        App.getComponent().injectContactWorkViewModel(this);

        contactWorkModelLiveData = new MutableLiveData<>();

        errorLiveData = new MutableLiveData<>();
    }


    public LiveData<ContactWorkModel> getContactWorkLiveData() {
        return contactWorkModelLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    public void getContactWorks() {
        CompletableFuture
                .supplyAsync(() -> contactWorkDao.getAll())
                .thenAccept(contactWorksFromDB -> {
                    if (contactWorksFromDB != null) {
                        contactWorkModelLiveData.postValue(new ContactWorkModel(contactWorksFromDB));
                    }

                    CompletableFuture
                            .supplyAsync(this::getContactWork)
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
                });
    }


    private List<ContactWork> getContactWork() {
        Document doc = new Connector().get(REMOTE_URL);

        return doc == null ? null : new ContactWorkParser().getContactWorks(doc);
    }
}
