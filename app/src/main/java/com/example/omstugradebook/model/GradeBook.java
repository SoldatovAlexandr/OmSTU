package com.example.omstugradebook.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class GradeBook {
    private List<Term> terms;
    private Student student;

    public GradeBook() {
    }

    public GradeBook(List<Term> terms, Student student) {
        this.terms = terms;
        this.student = student;
    }

    public Student getUser() {
        return student;
    }

    public void setUser(Student student) {
        this.student = student;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    @NonNull
    @Override
    public String toString() {
        return "GradeBook{" +
                "terms=" + terms +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeBook gradeBook = (GradeBook) o;
        return Objects.equals(terms, gradeBook.terms) &&
                Objects.equals(student, gradeBook.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms, student);
    }
}
