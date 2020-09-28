package com.example.omstugradebook.model.contactwork;

import java.util.Objects;

public class ContactWorksTask {
    private int number;
    private String comment;
    private String teacher;
    private String file;
    private String date;
    private String link;

    public ContactWorksTask(int number, String comment, String teacher, String file, String date, String link) {
        this.number = number;
        this.comment = comment;
        this.teacher = teacher;
        this.file = file;
        this.date = date;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ContactWorksTask{" +
                "comment='" + comment + '\'' +
                ", teacher='" + teacher + '\'' +
                ", file='" + file + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactWorksTask that = (ContactWorksTask) o;
        return Objects.equals(comment, that.comment) &&
                Objects.equals(teacher, that.teacher) &&
                Objects.equals(file, that.file) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, teacher, file, date);
    }
}
