package com.example.omstugradebook.model.grade;

import java.util.Objects;

public class User {
    private long id;
    private String login;
    private String password;
    private String token;

    private Student student;
    private long isActive;

    public User(String login, String password, String token) {
        this(0, login, password, token, new Student("-", "-", "-", "-"), 0);
    }

    public User(long id, String login, String password, String token, Student student, long isActive) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.token = token;
        this.student = student;
        this.isActive = isActive;
    }

    public long getIsActive() {
        return isActive;
    }

    public void setIsActive(long isActive) {
        this.isActive = isActive;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", student=" + student +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(token, user.token) &&
                Objects.equals(student, user.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, token, student);
    }
}
