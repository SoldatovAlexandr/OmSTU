package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.grade.Subject;

import java.util.List;

public interface SubjectDao {
    int removeAllSubjects();

    int removeSubjectsById(long id);

    List<Subject> readSubjectsByTerm(int termFilter);

    List<Subject> readAllSubjects();

    boolean insertAllSubjects(List<Subject> subjects);

    boolean equalsSubjects(List<Subject> subjects);

    int getCountTerm();

    long getCount();

    List<Subject> readSubjectsByUser(long userId);
}
