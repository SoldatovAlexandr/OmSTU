package com.example.omstugradebook.data.model.grade;

import androidx.room.Entity;

import java.util.Objects;

@Entity
public class Student {
    private String fullName;

    private String numberGradeBook;

    private String speciality;

    private String educationForm;

    public Student(String fullName, String numberGradeBook, String speciality, String educationForm) {
        this.fullName = fullName;

        this.numberGradeBook = numberGradeBook;

        this.speciality = speciality;

        this.educationForm = educationForm;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNumberGradeBook() {
        return numberGradeBook;
    }

    public String getSpeciality() {
        return speciality;
    }


    public String getEducationForm() {
        return educationForm;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNumberGradeBook(String numberGradeBook) {
        this.numberGradeBook = numberGradeBook;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", numberGradeBook='" + numberGradeBook + '\'' +
                ", Speciality='" + speciality + '\'' +
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
                Objects.equals(speciality, user.speciality) &&
                Objects.equals(educationForm, user.educationForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, numberGradeBook, speciality, educationForm);
    }
}
