package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;

import java.util.List;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void checkAuthActiveUser() {
        User user = DataBaseManager.getUserDao().getActiveUser();
        if (user != null) {
            new OmSTUSender().execute(user.getLogin(), user.getPassword());
        } else {
            errorLiveData.postValue("Никто не авторизован!");
        }
    }

    public void getUsers() {
        usersLiveData.postValue(DataBaseManager.getUserDao().readAllUsers());
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            AuthService auth = new AuthService();
            String cookie = auth.getAuth(strings[0], strings[1]);
            if (cookie.equals("error")) {
                errorLiveData.postValue("Ошибка подключения к серверу!");
                return null;
            }
            UserDao userDao = DataBaseManager.getUserDao();
            User activeUser = userDao.getActiveUser();
            activeUser.setToken(auth.getStudSessId(cookie));
            userDao.update(activeUser);
            return null;
        }
    }
}
