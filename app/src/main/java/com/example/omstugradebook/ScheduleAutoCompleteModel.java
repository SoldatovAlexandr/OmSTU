package com.example.omstugradebook;

import com.example.omstugradebook.model.schedule.ScheduleOwner;

public class ScheduleAutoCompleteModel {
    private final ScheduleOwner scheduleOwner;
    private final boolean isFavorite;

    public ScheduleAutoCompleteModel(ScheduleOwner scheduleOwner, boolean isFavorite) {
        this.scheduleOwner = scheduleOwner;
        this.isFavorite = isFavorite;
    }

    public ScheduleOwner getScheduleOwner() {
        return scheduleOwner;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

}
