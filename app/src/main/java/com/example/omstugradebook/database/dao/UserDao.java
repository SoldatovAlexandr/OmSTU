package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.grade.User;

import java.util.List;

public interface UserDao {
    long insert(User user);

    User getUserByLogin(String login);

    User getUserByToken(String token);

    User getUserById(long id);

    User getActiveUser();

    int removeAllUsers();

    long update(User user);

    List<User> readAllUsers();

    void changeActiveUser(User newUser);

    boolean removeUser(User user);
}
