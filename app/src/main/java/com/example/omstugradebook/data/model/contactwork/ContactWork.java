package com.example.omstugradebook.data.model.contactwork;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class ContactWork {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String discipline;

    private String teacher;

    private String numberOfTasks;

    private String taskLink;


    public ContactWork(long id, String discipline, String teacher, String numberOfTasks, String taskLink) {
        this.id = id;

        this.discipline = discipline;

        this.teacher = teacher;

        this.numberOfTasks = numberOfTasks;

        this.taskLink = taskLink;
    }

    @Ignore
    public ContactWork(String discipline, String teacher, String numberOfTasks, String taskLink) {
        this(0, discipline, teacher, numberOfTasks, taskLink);
    }

    public long getId() {
        return id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getNumberOfTasks() {
        return numberOfTasks;
    }

    public String getTaskLink() {
        return taskLink;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setNumberOfTasks(String numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public void setTaskLink(String taskLink) {
        this.taskLink = taskLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactWork that = (ContactWork) o;
        return Objects.equals(discipline, that.discipline) &&
                Objects.equals(teacher, that.teacher) &&
                Objects.equals(numberOfTasks, that.numberOfTasks) &&
                Objects.equals(taskLink, that.taskLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discipline, teacher, numberOfTasks, taskLink);
    }

    @Override
    public String toString() {
        return "ContactWork{" +
                "discipline='" + discipline + '\'' +
                ", teacher='" + teacher + '\'' +
                ", numberOfTasks='" + numberOfTasks + '\'' +
                ", taskLink='" + taskLink + '\'' +
                '}';
    }

}
