package com.example.omstugradebook.presentation.module;

import androidx.room.Room;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.ContactWorkDao;
import com.example.omstugradebook.data.dao.ScheduleDao;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.dao.SearchScheduleDao;
import com.example.omstugradebook.data.dao.SubjectDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.database.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {
    private static AppDatabase database;

    private static final String DATABASE_NAME = "populus-database";

    public StorageModule() {
        database = Room.databaseBuilder(
                App.getContext(),
                AppDatabase.class,
                DATABASE_NAME).build();
    }

    @Provides
    ContactWorkDao provideContactWorkDao() {
        return database.getContactWorkDao();
    }

    @Provides
    UserDao provideUserDao() {
        return database.getUserDao();
    }

    @Provides
    ScheduleDao provideScheduleDao() {
        return database.getScheduleDao();
    }

    @Provides
    SubjectDao provideSubjectDao() {
        return database.getSubjectDao();
    }

    @Provides
    ScheduleOwnerDao provideScheduleOwnerDao() {
        return database.getScheduleOwnerDao();
    }

    @Provides
    SearchScheduleDao provideSearchScheduleDao() {
        return database.getSearchScheduleDao();
    }

}