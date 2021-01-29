package com.example.omstugradebook.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.omstugradebook.data.model.schedule.ScheduleOwner;

import java.util.List;

@Dao
public interface ScheduleOwnerDao {
    @Insert
    void insertAll(ScheduleOwner... scheduleOwners);

    @Delete
    void delete(ScheduleOwner scheduleOwner);

    @Query("SELECT * FROM scheduleowner")
    List<ScheduleOwner> getAll();

    @Query("SELECT * FROM scheduleowner WHERE name LIKE :name")
    ScheduleOwner get(String name);

    @Query("SELECT * FROM scheduleowner WHERE name LIKE :name")
    boolean checkFavoriteSchedule(String name);
}
