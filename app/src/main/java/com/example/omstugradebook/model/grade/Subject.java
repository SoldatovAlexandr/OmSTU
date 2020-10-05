package com.example.omstugradebook.model.grade;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Subject {
    private final String name;
    private final String hours;
    private final String attendance;//не обязательно
    private final String tempRating;//не обязательно
    private final String mark;
    private final String date;
    private final String teacher;
    private final String toDiploma;
    private final int term;
    private final SubjectType type;
    private int userId;

    public Subject(String name, String hours, String attendance, String tempRating, String mark, String date, String teacher, String toDiploma, int term, SubjectType type, int userId) {
        this.name = name;
        this.hours = hours;
        this.attendance = attendance;
        this.tempRating = tempRating;
        this.mark = mark;
        this.date = date;
        this.teacher = teacher;
        this.toDiploma = toDiploma;
        this.term = term;
        this.type = type;
        this.userId = userId;
    }

    public Subject(String name, String hours, String attendance, String tempRating, String mark, String date, String teacher, String toDiploma, int term, SubjectType type) {
        this(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, type, 0);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SubjectType getType() {
        return type;
    }


    public int getTerm() {
        return term;
    }


    public String getName() {
        return name;
    }


    public String getHours() {
        return hours;
    }


    public String getAttendance() {
        return attendance;
    }


    public String getTempRating() {
        return tempRating;
    }


    public String getMark() {
        return mark;
    }


    public String getDate() {
        return date;
    }


    public String getTeacher() {
        return teacher;
    }


    public String getToDiploma() {
        return toDiploma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return term == subject.term &&
                type == subject.type &&
                Objects.equals(name, subject.name) &&
                Objects.equals(hours, subject.hours) &&
                Objects.equals(attendance, subject.attendance) &&
                Objects.equals(tempRating, subject.tempRating) &&
                Objects.equals(mark, subject.mark) &&
                Objects.equals(date, subject.date) &&
                Objects.equals(teacher, subject.teacher) &&
                Objects.equals(toDiploma, subject.toDiploma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, type);
    }

    @NonNull
    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", hours='" + hours + '\'' +
                ", attendance='" + attendance + '\'' +
                ", tempRating='" + tempRating + '\'' +
                ", mark='" + mark + '\'' +
                ", date='" + date + '\'' +
                ", teacher='" + teacher + '\'' +
                ", toDiploma='" + toDiploma + '\'' +
                ", term=" + term +
                ", type=" + type +
                '}';
    }
}
