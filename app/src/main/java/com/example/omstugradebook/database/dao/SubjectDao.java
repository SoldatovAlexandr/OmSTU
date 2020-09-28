package com.example.omstugradebook.database.dao;

import android.content.Context;

import com.example.omstugradebook.model.grade.Subject;

import java.util.List;

public interface SubjectDao {
    int removeAllSubjects(Context context);

    List<Subject> readSubjectsByTerm(int termFilter, Context context);

    List<Subject> readAllSubjects(Context context);

    boolean insertAllSubjects(List<Subject> subjects, Context context);

    boolean equalsSubjects(List<Subject> subjects, Context context);

    int getCountTerm(Context context);

    long getCount(Context context);

    List<Subject> getSubjectsByUser(int userId, int termFilter, Context context);
}
