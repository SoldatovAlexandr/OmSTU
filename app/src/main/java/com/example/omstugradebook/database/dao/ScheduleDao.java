package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedule();

    List<Schedule> readScheduleByGroup(String group);

    List<Schedule> readAllSchedule();

    boolean insertAllSchedule(List<Schedule> schedules);

    boolean equalsSchedule(List<Schedule> schedules);

}
