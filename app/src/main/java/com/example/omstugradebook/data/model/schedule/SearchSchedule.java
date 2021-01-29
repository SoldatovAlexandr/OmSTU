package com.example.omstugradebook.data.model.schedule;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.omstugradebook.data.model.converter.CalendarConverter;

import java.util.Calendar;

@Entity
public class SearchSchedule {
    @PrimaryKey
    private long searchScheduleId;

    private long id;

    private String name;

    private String type;

    @TypeConverters({CalendarConverter.class})
    private Calendar calendar;

    public SearchSchedule(long searchScheduleId, long id, String name, String type, Calendar calendar) {
        this.searchScheduleId = searchScheduleId;
        this.id = id;
        this.name = name;
        this.type = type;
        this.calendar = calendar;
    }

    @Ignore
    public SearchSchedule(long id, String name, String type, Calendar calendar) {
        this(1, id, name, type, calendar);
    }

    public long getSearchScheduleId() {
        return searchScheduleId;
    }

    public void setSearchScheduleId(long searchScheduleId) {
        this.searchScheduleId = searchScheduleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
