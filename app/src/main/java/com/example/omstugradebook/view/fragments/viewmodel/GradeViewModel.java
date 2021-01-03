package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.SubjectDao;
import com.example.omstugradebook.dao.UserDao;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.view.fragments.model.GradeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GradeViewModel extends ViewModel {
    private final MutableLiveData<String> errorLiveData;

    private final MutableLiveData<GradeModel> gradeLiveData;

    private final MutableLiveData<String> titleLiveData;

    @Inject
    UserDao userDao;

    @Inject
    SubjectDao subjectDao;

    public GradeViewModel() {
        App.getComponent().injectGradeViewModel(this);

        errorLiveData = new MutableLiveData<>();

        gradeLiveData = new MutableLiveData<>();

        titleLiveData = new MutableLiveData<>();
    }

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
        CompletableFuture.runAsync(() -> {

            List<Subject> subjectsFromDB = subjectDao.getAll();

            GradeModel gradeModel = makeGradeModel(subjectsFromDB, selectTerm, subjectDao.getCountTerm());

            gradeLiveData.postValue(gradeModel);

            sendRequest(selectTerm);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendRequest(int selectTerm) {
        CompletableFuture.supplyAsync(() -> new AuthService().getGradeBook())
                .thenAccept(gradeBook -> {
                            if (gradeBook == null) {
                                errorLiveData.postValue("Проблемы с подключением к серверу ОмГТУ");
                            } else {
                                List<Subject> subjectsFromDB = subjectDao.getAll();

                                List<Subject> subjects = getSubjects(gradeBook.getTerms());

                                if (!subjects.equals(subjectsFromDB)) {
                                    subjectDao.insertAll(subjects);

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
