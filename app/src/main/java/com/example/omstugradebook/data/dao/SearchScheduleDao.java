package com.example.omstugradebook.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.omstugradebook.data.model.schedule.SearchSchedule;

@Dao
public interface SearchScheduleDao {
    @Query("SELECT * FROM searchschedule WHERE searchScheduleId LIKE 1")
    SearchSchedule get();

    @Insert
    void insert(SearchSchedule searchSchedule);

    @Query("DELETE FROM searchschedule")
    void delete();

    @Update
    void update(SearchSchedule searchSchedule);
}
