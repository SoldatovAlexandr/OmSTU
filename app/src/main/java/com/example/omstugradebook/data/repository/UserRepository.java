package com.example.omstugradebook.data.repository;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.grade.User;

import javax.inject.Inject;

public class UserRepository {

    @Inject
    UserDao userDao;

    public UserRepository() {
        App.getComponent().injectStartRepository(this);
    }

    public boolean getUser() {
        return userDao.get() != null;
    }


    public void insert(User user) {
        userDao.insert(user);
    }
}
