package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedules();

    int removeSchedulesById(long id);

    List<Schedule> readScheduleByUserId(long userId);

    List<Schedule> readAllSchedule();

    boolean insertAllSchedule(List<Schedule> schedules);

    boolean equalsSchedule(List<Schedule> schedules);

}
