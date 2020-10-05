package com.example.omstugradebook.view.fragments.model;

import com.example.omstugradebook.model.grade.Subject;

import java.util.List;
import java.util.Objects;

public class GradeModel {
    private final List<Subject> subjects;
    private final int countTerms;

    public GradeModel(List<Subject> subjects, int countTerms) {
        this.subjects = subjects;
        this.countTerms = countTerms;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getCountTerms() {
        return countTerms;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "subjects=" + subjects +
                ", countTerms=" + countTerms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeModel that = (GradeModel) o;
        return countTerms == that.countTerms &&
                Objects.equals(subjects, that.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjects, countTerms);
    }
}
