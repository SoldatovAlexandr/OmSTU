package com.example.omstugradebook.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.omstugradebook.data.model.contactwork.ContactWork;

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
