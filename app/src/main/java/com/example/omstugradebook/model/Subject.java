package com.example.omstugradebook.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Subject {
    private String name;
    private String hours;
    private String attendance;//не обязательно
    private String tempRating;//не обязательно
    private String mark;
    private String date;
    private String teacher;
    private String toDiploma;
    private int term;
    // 0  exam
    // 1  test
    // 2  practice
    // 3  course work
    // 4  dif test
    private int type;

    public Subject(String name, String hours, String attendance, String tempRating, String mark, String date, String teacher, String toDiploma, int term, int type) {
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
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getTempRating() {
        return tempRating;
    }

    public void setTempRating(String tempRating) {
        this.tempRating = tempRating;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getToDiploma() {
        return toDiploma;
    }

    public void setToDiploma(String toDiploma) {
        this.toDiploma = toDiploma;
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
