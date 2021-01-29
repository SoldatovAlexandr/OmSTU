package com.example.omstugradebook.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.omstugradebook.data.model.grade.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE id = 1")
    User get();

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
