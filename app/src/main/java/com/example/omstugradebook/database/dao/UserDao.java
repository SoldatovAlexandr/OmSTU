package com.example.omstugradebook.database.dao;

import android.content.Context;

import com.example.omstugradebook.model.grade.User;

import java.util.List;

public interface UserDao {
    long insert(User user, Context context);

    User getUserByLogin(String login, Context context);

    User getUserByToken(String token, Context context);

    User getUserById(long id, Context context);

    User getActiveUser(Context context);

    int removeAllUsers(Context context);

    long update(User user, Context context);

    List<User> readAllUsers(Context context);

    void changeActiveUser(User newUser, Context context);

    boolean removeUser(User user, Context context);
}
