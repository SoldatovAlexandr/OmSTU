package com.example.omstugradebook.view.activity.model;

import com.example.omstugradebook.model.schedule.ScheduleOwner;

import java.util.Calendar;
import java.util.Objects;

public class SearchStateModel {
    private final ScheduleOwner scheduleOwner;
    private final Calendar calendar;

    public SearchStateModel(ScheduleOwner scheduleOwner, Calendar calendar) {
        this.scheduleOwner = scheduleOwner;
        this.calendar = calendar;

    }

    public ScheduleOwner getScheduleOwner() {
        return scheduleOwner;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchStateModel that = (SearchStateModel) o;
        return Objects.equals(scheduleOwner, that.scheduleOwner) &&
                Objects.equals(calendar, that.calendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleOwner, calendar);
    }
}
