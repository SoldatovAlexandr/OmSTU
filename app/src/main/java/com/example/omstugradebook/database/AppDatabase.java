package com.example.omstugradebook.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.omstugradebook.dao.ContactWorkDao;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.dao.SubjectDao;
import com.example.omstugradebook.dao.UserDao;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.model.schedule.ScheduleOwner;

@Database(entities = {ScheduleOwner.class, User.class, Subject.class, Schedule.class, ContactWork.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduleOwnerDao getScheduleOwnerDao();

    public abstract UserDao getUserDao();

    public abstract SubjectDao getSubjectDao();

    public abstract ScheduleDao getScheduleDao();

    public abstract ContactWorkDao getContactWorkDao();
}
