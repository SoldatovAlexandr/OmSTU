package com.example.omstugradebook.view.fragments.model;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public class TimetableModel {
    private final List<Schedule> schedules;

    public TimetableModel(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
