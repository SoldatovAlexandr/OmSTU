package com.example.omstugradebook.model.contactwork;

public class ContactWork {
    private String discipline;
    private String teacher;
    private String numberOfTasks;
    private String taskLink;
    private int userId;

    public ContactWork(String discipline, String teacher, String numberOfTasks, String taskLink, int userId) {
        this.discipline = discipline;
        this.teacher = teacher;
        this.numberOfTasks = numberOfTasks;
        this.taskLink = taskLink;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(String numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public String getTaskLink() {
        return taskLink;
    }

    public void setTaskLink(String taskLink) {
        this.taskLink = taskLink;
    }
}
