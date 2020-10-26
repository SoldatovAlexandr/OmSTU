package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.grade.User;

public interface UserDao {
    long insert(User user);

    User getUserByLogin(String login);

    User getUser();

    boolean removeUser(User user);

    long update(User user);
}
