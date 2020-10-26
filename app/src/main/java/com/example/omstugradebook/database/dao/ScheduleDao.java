package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedules();

    List<Schedule> readAllSchedules();

    boolean insertAllSchedule(List<Schedule> schedules);

    boolean insertFavoriteSchedule(String value);

    List<String> readFavoriteSchedule();

    int removeAllFavoriteSchedules();

    int removeFavoriteSchedule(String value);
}
