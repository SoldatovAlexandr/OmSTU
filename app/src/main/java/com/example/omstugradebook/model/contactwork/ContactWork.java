package com.example.omstugradebook.model.contactwork;

import java.util.Objects;

public class ContactWork {
    private final String discipline;
    private final String teacher;
    private final String numberOfTasks;
    private final String taskLink;


    public ContactWork(String discipline, String teacher, String numberOfTasks, String taskLink) {
        this.discipline = discipline;
        this.teacher = teacher;
        this.numberOfTasks = numberOfTasks;
        this.taskLink = taskLink;
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
