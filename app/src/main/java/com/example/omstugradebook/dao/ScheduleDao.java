package com.example.omstugradebook.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.omstugradebook.model.schedule.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("DELETE FROM schedule")
    void deleteAll();

    @Insert
    void insert(List<Schedule> schedules);

    @Query("DELETE FROM schedule WHERE favoriteId LIKE :favoriteId")
    void deleteByFavoriteId(long favoriteId);

    @Query("SELECT * FROM schedule WHERE favoriteId LIKE :favoriteId")
    List<Schedule> getByFavoriteId(long favoriteId);
}


