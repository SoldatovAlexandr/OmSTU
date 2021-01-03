package com.example.omstugradebook.view.activity.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.R;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.dao.SubjectDao;
import com.example.omstugradebook.dao.UserDao;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData;

    private final MutableLiveData<Integer> errorLiveData;

    private final MutableLiveData<Integer> infoLiveData;

    @Inject
    UserDao userDao;

    @Inject
    SubjectDao subjectDao;

    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    public LoginViewModel() {
        App.getComponent().injectLoginViewModel(this);

        userLiveData = new MutableLiveData<>();

        errorLiveData = new MutableLiveData<>();

        infoLiveData = new MutableLiveData<>();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Integer> getInfoLiveData() {
        return infoLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void signIn(String login, String password) {
        sendRequest(login, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendRequest(String login, String password) {
        CompletableFuture.supplyAsync(() -> {
            AuthService auth = new AuthService();

            String cookie = auth.getAuth(login, password);

            List<String> response = new ArrayList<>();

            response.add(login);

            response.add(password);

            response.add(auth.getStudSessId(cookie));

            return response;
        }).thenAccept(strings -> {
            if (strings != null && strings.size() == 3 && strings.get(2) != null) {
                infoLiveData.postValue((R.string.loginSuccsessfullyString));

                AuthService auth = new AuthService();

                String studSesId = strings.get(2);

                GradeBook gradeBook = auth.getGradeBook(studSesId);

                if (gradeBook != null) {
                    User user = new User(strings.get(0), strings.get(1), studSesId, gradeBook.getStudent());

                    List<Subject> subjects = new ArrayList<>();

                    for (Term term : gradeBook.getTerms()) {
                        subjects.addAll(term.getSubjects());
                    }

                    userLiveData.postValue(user);

                    userDao.insert(user);

                    subjectDao.insertAll(subjects);

                    insertScheduleOwner(user.getStudent().getSpeciality());
                } else {
                    errorLiveData.postValue(R.string.serverException);
                }

            } else {
                errorLiveData.postValue((R.string.wrongLoginOrPasswordString));
            }
        });

    }

    private void insertScheduleOwner(String group) {
        ScheduleService scheduleService = new ScheduleService();

        List<ScheduleOwner> scheduleOwners = scheduleService.getScheduleOwners(group, true);

        if (!scheduleOwners.isEmpty()) {
            scheduleOwnerDao.insertAll(scheduleOwners.get(0));
        }
    }

}
