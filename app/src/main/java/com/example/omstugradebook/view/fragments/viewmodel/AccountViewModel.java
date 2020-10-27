package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;

public class AccountViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }


    public void logoutUser() {
        DataBaseManager.clearDataBase();
    }

    public void getUser() {
        userLiveData.postValue(DataBaseManager.getUserDao().getUser());
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
            User activeUser = userDao.getUser();
            if (activeUser != null) {
                activeUser.setToken(auth.getStudSessId(cookie));
                userDao.update(activeUser);
            }
            return null;
        }
    }
}
