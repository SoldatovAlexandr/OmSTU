package com.example.omstugradebook.view.activity.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> errorLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public void signIn(String login, String password) {
        if (DataBaseManager.getUserDao().getUserByLogin(login) == null) {
            new LoginSender().execute(login, password);
        } else {
            errorLiveData.postValue(R.string.thisUserIsAlreadyLoggedIn);
        }
    }

    class LoginSender extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            AuthService auth = new AuthService();
            String login = strings[0];
            String password = strings[1];
            String cookie = auth.getAuth(login, password);
            List<String> response = new ArrayList<>();
            response.add(login);
            response.add(password);
            response.add(auth.getStudSessId(cookie));
            return response;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if (!strings.isEmpty()) {
                errorLiveData.postValue((R.string.loginSuccsessfullyString));
                new UserDataRequestSender().execute(strings.get(0), strings.get(1), strings.get(2));
            } else {
                errorLiveData.postValue((R.string.wrongLoginOrPasswordString));
            }
        }

        class UserDataRequestSender extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                AuthService auth = new AuthService();
                String studSesId = strings[2];
                GradeBook gradeBook = auth.getGradeBook(studSesId);
                if (gradeBook != null) {
                    User user = new User(strings[0], strings[1], studSesId, gradeBook.getStudent());
                    List<Subject> subjects = new ArrayList<>();
                    for (Term term : gradeBook.getTerms()) {
                        subjects.addAll(term.getSubjects());
                    }
                    for (Subject subject : subjects) {
                        subject.setUserId(user.getId());
                    }
                    userLiveData.postValue(user);
                    SubjectDao subjectDao = DataBaseManager.getSubjectDao();
                    UserDao userDao = DataBaseManager.getUserDao();
                    userDao.insert(user);
                    userDao.changeActiveUser(user);
                    subjectDao.insertAllSubjects(subjects);
                }
                return null;
            }
        }
    }
}
