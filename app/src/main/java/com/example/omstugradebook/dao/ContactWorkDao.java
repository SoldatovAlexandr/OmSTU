package com.example.omstugradebook.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.omstugradebook.model.contactwork.ContactWork;

import java.util.List;

@Dao
public interface ContactWorkDao {
    @Query("DELETE FROM contactWork")
    void deleteAll();

    @Query("SELECT * FROM ContactWork")
    List<ContactWork> getAll();

    @Insert
    void insertAll(List<ContactWork> contactWorks);
}
