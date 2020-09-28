package com.example.omstugradebook.database.dao;

import android.content.Context;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedule(Context context);

    List<Schedule> readScheduleByGroup(String group, Context context);

    List<Schedule> readAllSchedule(Context context);

    boolean insertAllSchedule(List<Schedule> schedules, Context context);

    boolean equalsSchedule(List<Schedule> schedules, Context context);

}
