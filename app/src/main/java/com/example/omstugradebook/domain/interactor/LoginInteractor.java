package com.example.omstugradebook.domain.interactor;

import com.example.omstugradebook.App;
import com.example.omstugradebook.AppException;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.dao.SearchScheduleDao;
import com.example.omstugradebook.data.dao.SubjectDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.grade.GradeBook;
import com.example.omstugradebook.data.model.grade.Subject;
import com.example.omstugradebook.data.model.grade.Term;
import com.example.omstugradebook.data.model.grade.User;
import com.example.omstugradebook.data.model.schedule.ScheduleOwner;
import com.example.omstugradebook.data.model.schedule.SearchSchedule;
import com.example.omstugradebook.domain.connector.AuthService;
import com.example.omstugradebook.domain.connector.ScheduleService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class LoginInteractor {
    @Inject
    UserDao userDao;

    @Inject
    SubjectDao subjectDao;

    @Inject
    SearchScheduleDao searchScheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    private final AuthService authService = new AuthService();

    public LoginInteractor() {
        App.getComponent().injectLoginInteractor(this);
    }

    public User signIn(String login, String password) throws AppException {
        String token = getSession(login, password);

        return getUser(login, password, token);
    }

    private String getSession(String login, String password) throws AppException {
        String cookie = authService.getAuth(login, password);
        if (cookie == null) {
            throw new AppException();
        }
        return authService.getStudSessId(cookie);
    }

    private User getUser(String login, String password, String token) throws AppException {
        GradeBook gradeBook = authService.getGradeBook(token);

        if (gradeBook != null) {
            User user = new User(login, password, token, gradeBook.getStudent());

            initDataBase(gradeBook, user);

            return user;
        } else {
            throw new AppException();
        }
    }

    private void initDataBase(GradeBook gradeBook, User user) {
        List<Subject> subjects = new ArrayList<>();

        for (Term term : gradeBook.getTerms()) {
            subjects.addAll(term.getSubjects());
        }

        SearchSchedule searchSchedule = getSearchSchedule(user);

        userDao.insert(user);

        subjectDao.insertAll(subjects);

        searchScheduleDao.insert(searchSchedule);

        insertScheduleOwner(user.getStudent().getSpeciality());
    }

    private void insertScheduleOwner(String group) {
        ScheduleService scheduleService = new ScheduleService();

        List<ScheduleOwner> scheduleOwners = scheduleService.getScheduleOwners(group, true);

        if (!scheduleOwners.isEmpty()) {
            scheduleOwnerDao.insertAll(scheduleOwners.get(0));
        }
    }


    private SearchSchedule getSearchSchedule(User user) {
        ScheduleOwner scheduleOwner = new ScheduleService()
                .getScheduleOwners(user.getStudent().getSpeciality(), true)
                .get(0);

        return new SearchSchedule(
                scheduleOwner.getId(),
                scheduleOwner.getName(),
                scheduleOwner.getType(),
                Calendar.getInstance()
        );
    }
}
