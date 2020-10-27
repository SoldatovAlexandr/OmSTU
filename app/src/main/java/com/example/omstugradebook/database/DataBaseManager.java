package com.example.omstugradebook.database;

import com.example.omstugradebook.database.dao.ContactWorkDao;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.ContactWorkDaoImpl;
import com.example.omstugradebook.database.daoimpl.ScheduleDaoImpl;
import com.example.omstugradebook.database.daoimpl.SubjectDaoImpl;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;

public class DataBaseManager {
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static ScheduleDao getScheduleDao() {
        return new ScheduleDaoImpl();
    }

    public static SubjectDao getSubjectDao() {
        return new SubjectDaoImpl();
    }

    public static ContactWorkDao getContactWorkDao() {
        return new ContactWorkDaoImpl();
    }

    public static void clearDataBase() {
        UserDao userDao = getUserDao();
        userDao.removeUser(userDao.getUser());
        getScheduleDao().removeAllFavoriteSchedules();
        getScheduleDao().removeAllSchedules();
        getSubjectDao().removeAllSubjects();
        getContactWorkDao().removeAllToContactWork();
    }
}
