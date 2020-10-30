package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.model.schedule.ScheduleOwner;

import java.util.List;

public interface ScheduleDao {
    int removeAllSchedules();

    List<Schedule> readAllSchedules();

    ScheduleOwner getFavoriteScheduleByValue(String value);

    boolean insertAllSchedule(List<Schedule> schedules);

    //schedule owners

    boolean insertFavoriteSchedule(ScheduleOwner scheduleOwner);

    List<ScheduleOwner> readFavoriteSchedule();

    int removeAllFavoriteSchedules();

    int removeFavoriteSchedule(ScheduleOwner scheduleOwner);

    int removeSchedulesByFavoriteId(String favoriteId);

    List<Schedule> readSchedulesByFavoriteId(String favoriteId);
}


