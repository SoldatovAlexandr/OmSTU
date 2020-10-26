package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedules();

    int removeSchedulesById(long id);

    List<Schedule> readScheduleByUserId(long userId);

    boolean insertAllSchedule(List<Schedule> schedules);

    boolean insertFavoriteSchedule( long userId, String value);

    List<String> readFavoriteScheduleByUserId(long userId);

    int removeAllFavoriteSchedules(long userId);

    int removeFavoriteSchedule(long id, String value);
}
