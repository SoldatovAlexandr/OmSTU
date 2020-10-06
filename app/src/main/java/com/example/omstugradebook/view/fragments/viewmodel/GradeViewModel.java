package com.example.omstugradebook.view.fragments.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.view.fragments.model.GradeModel;

import java.util.ArrayList;
import java.util.List;

public class GradeViewModel extends ViewModel {
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<String> infoLiveData = new MutableLiveData<>();
    private MutableLiveData<GradeModel> gradeLiveData = new MutableLiveData<>();
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<String> getInfoLiveData() {
        return errorLiveData;
    }

    public LiveData<GradeModel> getSubjectsLiveData() {
        return gradeLiveData;
    }

    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public void getSubjects(String selectTerm) {
        User activeUser = DataBaseManager.getUserDao().getActiveUser();
        if (activeUser == null) {
            infoLiveData.postValue("Пожалуйста, войдите в ваш аккаует, чтобы пользоваться системой");
        } else {
            SubjectDao subjectDao = DataBaseManager.getSubjectDao();
            long id = activeUser.getId();
            List<Subject> subjectsFromDB = subjectDao.readSubjectsByUser(id);
            postValues(makeGradeModel(subjectsFromDB, Integer.parseInt(selectTerm), subjectDao.getCountTerm()));
            new OmSTUSender().execute(selectTerm, String.valueOf(id));
        }
    }

    private void postValues(GradeModel gradeModel) {
        gradeLiveData.postValue(gradeModel);
        infoLiveData.postValue("");
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

    class OmSTUSender extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            infoLiveData.postValue("");
            int selectTerm = Integer.parseInt(strings[0]);
            int id = Integer.parseInt(strings[1]);
            GradeBook gradeBook = new AuthService().getGradeBook();
            if (gradeBook == null) {
                errorLiveData.postValue("Проблемы с подключением к серверу ОмГТУ");
            } else {
                SubjectDao subjectDao = DataBaseManager.getSubjectDao();
                List<Subject> subjectsFromDB = subjectDao.readSubjectsByUser(id);
                List<Subject> subjects = getSubjects(gradeBook.getTerms());
                for (Subject subject : subjects) {
                    subject.setUserId(id);
                }
                if (!subjects.equals(subjectsFromDB)) {
                    subjectDao.removeAllSubjects();
                    subjectDao.insertAllSubjects(subjects);
                    postValues(makeGradeModel(subjects, selectTerm, subjectDao.getCountTerm()));
                }
            }
            return null;
        }
    }
}
