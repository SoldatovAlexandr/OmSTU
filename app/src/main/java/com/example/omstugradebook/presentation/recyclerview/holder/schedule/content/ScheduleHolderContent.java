package com.example.omstugradebook.presentation.recyclerview.holder.schedule.content;

import com.example.omstugradebook.data.model.schedule.Schedule;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;

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
