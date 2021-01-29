package com.example.omstugradebook.data.model.schedule;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    private long id;

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

    private String streamType;

    private String dayOfWeekString;

    private long favoriteId;

    public Schedule(long id, String auditorium, String beginLesson, String building, String date,
                    int dayOfWeek, String detailInfo, String discipline, String endLesson,
                    String kindOfWork, String lecturer, String streamType, String dayOfWeekString,
                    long favoriteId) {
        this.id = id;

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

        this.favoriteId = favoriteId;
    }

    @Ignore
    public Schedule(String auditorium, String beginLesson, String building, String date,
                    int dayOfWeek, String detailInfo, String discipline, String endLesson,
                    String kindOfWork, String lecturer, String streamType, String dayOfWeekString) {
        this(0, auditorium, beginLesson, building, date, dayOfWeek, detailInfo, discipline, endLesson,
                kindOfWork, lecturer, streamType, dayOfWeekString, 0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setBeginLesson(String beginLesson) {
        this.beginLesson = beginLesson;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setEndLesson(String endLesson) {
        this.endLesson = endLesson;
    }

    public void setKindOfWork(String kindOfWork) {
        this.kindOfWork = kindOfWork;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public void setDayOfWeekString(String dayOfWeekString) {
        this.dayOfWeekString = dayOfWeekString;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getDayOfWeekString() {
        return dayOfWeekString;
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
