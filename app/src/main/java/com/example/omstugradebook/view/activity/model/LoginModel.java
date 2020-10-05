package com.example.omstugradebook.view.activity.model;

import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.User;

import java.util.List;
import java.util.Objects;

public class LoginModel {
    private final User user;
    private final List<Subject> subjectList;

    public LoginModel(User user, List<Subject> subjectList) {
        this.user = user;
        this.subjectList = subjectList;
    }

    public User getUser() {
        return user;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "user=" + user +
                ", subjectList=" + subjectList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginModel that = (LoginModel) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(subjectList, that.subjectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, subjectList);
    }
}
