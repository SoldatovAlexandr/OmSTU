package com.example.omstugradebook.model.schedule;

import java.util.Objects;

public class Schedule {
    private String auditorium;
    private String beginLesson;
    private String building;
    private String date;
    private int dayOfWeek;
    private String detailInfo;
    private String discipline;
    private String endLesson;
    private String kindOfWork;
    private String lecturer;
    private String stream;
    private String group;
    private String subGroup;
    private String dayOfWeekString;

    public Schedule() {
    }

    public Schedule(String auditorium, String beginLesson, String building, String date, int dayOfWeek, String detailInfo, String discipline, String endLesson, String kindOfWork, String lecturer, String stream, String group, String subGroup, String dayOfWeekString) {
        this.auditorium = auditorium;
        this.beginLesson = beginLesson;
        this.building = building;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.detailInfo = detailInfo;
        this.discipline = discipline;
        this.endLesson = endLesson;
        this.kindOfWork = kindOfWork;
        this.lecturer = lecturer;
        this.stream = stream;
        this.group = group;
        this.subGroup = subGroup;
        this.dayOfWeekString = dayOfWeekString;
    }

    public String getDayOfWeekString() {
        return dayOfWeekString;
    }

    public void setDayOfWeekString(String dayOfWeekString) {
        this.dayOfWeekString = dayOfWeekString;
    }

    public String getDayOfWeekFullString() {
        switch (dayOfWeek) {
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
        }
        return "Воскресенье";
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getBeginLesson() {
        return beginLesson;
    }

    public void setBeginLesson(String beginLesson) {
        this.beginLesson = beginLesson;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(String endLesson) {
        this.endLesson = endLesson;
    }

    public String getKindOfWork() {
        return kindOfWork;
    }

    public void setKindOfWork(String kindOfWork) {
        this.kindOfWork = kindOfWork;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return dayOfWeek == schedule.dayOfWeek &&
                Objects.equals(auditorium, schedule.auditorium) &&
                Objects.equals(beginLesson, schedule.beginLesson) &&
                Objects.equals(building, schedule.building) &&
                Objects.equals(date, schedule.date) &&
                Objects.equals(detailInfo, schedule.detailInfo) &&
                Objects.equals(discipline, schedule.discipline) &&
                Objects.equals(endLesson, schedule.endLesson) &&
                Objects.equals(kindOfWork, schedule.kindOfWork) &&
                Objects.equals(lecturer, schedule.lecturer) &&
                Objects.equals(stream, schedule.stream) &&
                Objects.equals(group, schedule.group) &&
                Objects.equals(subGroup, schedule.subGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auditorium, beginLesson, building, date, dayOfWeek, detailInfo, discipline, endLesson, kindOfWork, lecturer, stream, group, subGroup);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "auditorium='" + auditorium + '\'' +
                ", beginLesson='" + beginLesson + '\'' +
                ", building='" + building + '\'' +
                ", date='" + date + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", detailInfo='" + detailInfo + '\'' +
                ", discipline='" + discipline + '\'' +
                ", endLesson='" + endLesson + '\'' +
                ", kindOfWork='" + kindOfWork + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", stream='" + stream + '\'' +
                ", group='" + group + '\'' +
                ", subGroup='" + subGroup + '\'' +
                '}';
    }
}
