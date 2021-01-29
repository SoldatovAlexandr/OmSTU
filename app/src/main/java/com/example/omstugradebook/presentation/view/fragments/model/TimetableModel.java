package com.example.omstugradebook.presentation.view.fragments.model;

import com.example.omstugradebook.data.model.schedule.Schedule;

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
