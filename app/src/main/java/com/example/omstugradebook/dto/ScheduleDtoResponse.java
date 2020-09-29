package com.example.omstugradebook.dto;

import java.io.Serializable;
import java.util.Objects;

public class ScheduleDtoResponse implements Serializable {

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
    private final String stream;
    private final String group;
    private final String subGroup;
    private final String dayOfWeekString;

    public ScheduleDtoResponse(String auditorium, String beginLesson, String building, String date, int dayOfWeek, String detailInfo, String discipline, String endLesson, String kindOfWork, String lecturer, String stream, String group, String subGroup, String dayOfWeekString) {
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

    public String getGroup() {
        return group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public String getDiscipline() {
        return discipline;
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

    public String getEndLesson() {
        return endLesson;
    }

    public String getKindOfWork() {
        return kindOfWork;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getStream() {
        return stream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDtoResponse that = (ScheduleDtoResponse) o;
        return dayOfWeek == that.dayOfWeek &&
                Objects.equals(auditorium, that.auditorium) &&
                Objects.equals(beginLesson, that.beginLesson) &&
                Objects.equals(building, that.building) &&
                Objects.equals(date, that.date) &&
                Objects.equals(detailInfo, that.detailInfo) &&
                Objects.equals(discipline, that.discipline) &&
                Objects.equals(endLesson, that.endLesson) &&
                Objects.equals(kindOfWork, that.kindOfWork) &&
                Objects.equals(lecturer, that.lecturer) &&
                Objects.equals(stream, that.stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auditorium, beginLesson, building, date, dayOfWeek, detailInfo, discipline, endLesson, kindOfWork, lecturer, stream);
    }

    @Override
    public String toString() {
        return "SubjectDtoResponse{" +
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
                '}';
    }
}
