package com.example.omstugradebook.recyclerview.holder.schedule.content;

import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.holder.HolderContent;

public class ScheduleHolderContent extends HolderContent {
    private final Schedule schedule;

    public ScheduleHolderContent(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public int getType() {
        return 1;
    }
}
