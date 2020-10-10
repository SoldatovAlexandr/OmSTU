package com.example.omstugradebook.model.schedule;

import java.util.Objects;

public class Schedule {
    private final String auditorium;
    private final String beginLesson;
    private final String building;
    private final String date;
    private final int dayOfWeek;
    private final String detailInfo;
    private final String discipline;
    private final String endLesson;
    private final String kindOfWork;
    private final String lecturer;
    private final String streamType;
    private final String dayOfWeekString;
    private final long userId;

    public Schedule(String auditorium, String beginLesson, String building, String date, int dayOfWeek, String detailInfo, String discipline, String endLesson, String kindOfWork, String lecturer, String streamType, String dayOfWeekString, long userId) {
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
        this.streamType = streamType;
        this.dayOfWeekString = dayOfWeekString;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getDayOfWeekString() {
        return dayOfWeekString;
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

    public String getBeginLesson() {
        return beginLesson;
    }

    public String getBuilding() {
        return building;
    }

    public String getDate() {
        return date;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getEndLesson() {
        return endLesson;
    }

    public String getKindOfWork() {
        return kindOfWork;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getStreamType() {
        return streamType;
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
                ", streamType='" + streamType + '\'' +
                ", dayOfWeekString='" + dayOfWeekString + '\'' +
                '}';
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
                Objects.equals(streamType, schedule.streamType) &&
                Objects.equals(dayOfWeekString, schedule.dayOfWeekString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auditorium, beginLesson, building, date, dayOfWeek, detailInfo, discipline, endLesson, kindOfWork, lecturer, streamType, dayOfWeekString);
    }
}
