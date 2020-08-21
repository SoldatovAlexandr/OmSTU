package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.Subject;

import java.util.List;

public interface SubjectDao {
    int removeAllSubjects();

    List<Subject> readSubjectsByTerm(int termFilter);

    List<Subject> readAllSubjects();

    void insertAllSubjects(List<Subject> subjects);

    boolean equalsSubjects(List<Subject> subjects);

    int getCountTerm();

    long getCount();
}
