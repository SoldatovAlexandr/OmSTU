package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.view.fragments.model.GradeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GradeViewModel extends ViewModel {
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<GradeModel> gradeLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> titleLiveData = new MutableLiveData<>();

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<GradeModel> getSubjectsLiveData() {
        return gradeLiveData;
    }

    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSubjects(int selectTerm) {
        User activeUser = DataBaseManager.getUserDao().getUser();
        if (activeUser == null) {
            errorLiveData.postValue("Пожалуйста, войдите в ваш аккаует, чтобы пользоваться системой");
        } else {
            SubjectDao subjectDao = DataBaseManager.getSubjectDao();
            long id = activeUser.getId();
            List<Subject> subjectsFromDB = subjectDao.readAllSubjects();
            GradeModel gradeModel = makeGradeModel(subjectsFromDB, selectTerm, subjectDao.getCountTerm());
            gradeLiveData.postValue(gradeModel);
            sendRequest(selectTerm, id);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendRequest(int selectTerm, long id) {

        CompletableFuture.supplyAsync(() -> new AuthService().getGradeBook())
                .thenAccept(gradeBook -> {
                            if (gradeBook == null) {
                                errorLiveData.postValue("Проблемы с подключением к серверу ОмГТУ");
                            } else {
                                SubjectDao subjectDao = DataBaseManager.getSubjectDao();
                                List<Subject> subjectsFromDB = subjectDao.readAllSubjects();
                                List<Subject> subjects = getSubjects(gradeBook.getTerms());
                                for (Subject subject : subjects) {
                                    subject.setUserId(id);
                                }
                                if (!subjects.equals(subjectsFromDB)) {
                                    subjectDao.readAllSubjects();
                                    subjectDao.insertAllSubjects(subjects);
                                    GradeModel gradeModel = makeGradeModel(subjects, selectTerm,
                                            subjectDao.getCountTerm());
                                    gradeLiveData.postValue(gradeModel);
                                }
                            }
                        }
                );
    }


    private List<Subject> getWithTerm(List<Subject> subjects, int term) {
        List<Subject> response = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getTerm() == term) {
                response.add(subject);
            }
        }
        titleLiveData.postValue("Семестр " + term);
        return response;
    }

    private GradeModel makeGradeModel(List<Subject> subjects, int selectTerm, int countTerms) {
        return new GradeModel(getWithTerm(subjects, selectTerm), countTerms);
    }

    private List<Subject> getSubjects(List<Term> terms) {
        List<Subject> subjects = new ArrayList<>();
        for (Term term : terms) {
            subjects.addAll(term.getSubjects());
        }
        return subjects;
    }
}
