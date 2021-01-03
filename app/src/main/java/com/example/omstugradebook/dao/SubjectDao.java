package com.example.omstugradebook.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.omstugradebook.model.grade.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("DELETE FROM subject")
    void deleteAll();

    @Query("SELECT * FROM subject")
    List<Subject> getAll();

    @Insert
    void insertAll(List<Subject> subjects);

    @Query("SELECT max(term) as term FROM subject")
    int getCountTerm();
}