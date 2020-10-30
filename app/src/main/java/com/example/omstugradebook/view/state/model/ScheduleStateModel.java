package com.example.omstugradebook.view.state.model;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public class ScheduleStateModel {
    private final List<Schedule> schedules;

    public ScheduleStateModel(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
