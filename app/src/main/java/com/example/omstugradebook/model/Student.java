package com.example.omstugradebook.model;

import java.util.Objects;

public class Student {
    private String fullName;
    private String numberGradeBook;
    private String Speciality;
    private String educationForm;

    public Student(String fullName, String numberGradeBook, String speciality, String educationForm) {
        this.fullName = fullName;
        this.numberGradeBook = numberGradeBook;
        Speciality = speciality;
        this.educationForm = educationForm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumberGradeBook() {
        return numberGradeBook;
    }

    public void setNumberGradeBook(String numberGradeBook) {
        this.numberGradeBook = numberGradeBook;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", numberGradeBook='" + numberGradeBook + '\'' +
                ", Speciality='" + Speciality + '\'' +
                ", educationForm='" + educationForm + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student user = (Student) o;
        return Objects.equals(fullName, user.fullName) &&
                Objects.equals(numberGradeBook, user.numberGradeBook) &&
                Objects.equals(Speciality, user.Speciality) &&
                Objects.equals(educationForm, user.educationForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, numberGradeBook, Speciality, educationForm);
    }
}
