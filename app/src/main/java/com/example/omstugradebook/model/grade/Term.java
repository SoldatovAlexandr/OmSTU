package com.example.omstugradebook.model.grade;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Term {
    private List<Subject> subjects;

    public Term() {
    }

    public Term(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public String toString() {
        return "Term{" +
                "subjects=" + subjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equals(subjects, term.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjects);
    }
}
