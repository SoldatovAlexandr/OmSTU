package com.example.omstugradebook.module;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ContactWorkDao;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.dao.SubjectDao;
import com.example.omstugradebook.dao.UserDao;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {
    @Provides
    ContactWorkDao provideContactWorkUtils() {
        return App.getDatabase().getContactWorkDao();
    }

    @Provides
    UserDao provideUserDaoUtils() {
        return App.getDatabase().getUserDao();
    }

    @Provides
    ScheduleDao provideScheduleDaoUtils() {
        return App.getDatabase().getScheduleDao();
    }

    @Provides
    SubjectDao provideSubjectDaoUtils() {
        return App.getDatabase().getSubjectDao();
    }

    @Provides
    ScheduleOwnerDao scheduleOwnerDao() {
        return App.getDatabase().getScheduleOwnerDao();
    }
}
