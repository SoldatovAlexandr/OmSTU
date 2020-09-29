package com.example.omstugradebook.model.contactwork;

import java.util.Objects;

public class ContactWorksTask {
    private final int number;
    private final String comment;
    private final String teacher;
    private final String file;
    private final String date;
    private final String link;

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

    public int getNumber() {
        return number;
    }

    public String getComment() {
        return comment;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getFile() {
        return file;
    }

    public String getDate() {
        return date;
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
