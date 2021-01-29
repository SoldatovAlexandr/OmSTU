package com.example.omstugradebook.data.model.grade;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.omstugradebook.data.model.converter.SubjectTypeConverter;

import java.util.Objects;

@Entity
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String hours;

    private String attendance;

    private String tempRating;

    private String mark;

    private String date;

    private String teacher;

    private String toDiploma;

    private int term;

    @TypeConverters({SubjectTypeConverter.class})
    private SubjectType type;

    public Subject(String name, String hours, String attendance,
                   String tempRating, String mark, String date,
                   String teacher, String toDiploma, int term, SubjectType type) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public void setTempRating(String tempRating) {
        this.tempRating = tempRating;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setToDiploma(String toDiploma) {
        this.toDiploma = toDiploma;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setType(SubjectType type) {
        this.type = type;
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
