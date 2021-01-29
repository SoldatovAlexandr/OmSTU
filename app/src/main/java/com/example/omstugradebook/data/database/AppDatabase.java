package com.example.omstugradebook.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.omstugradebook.data.dao.ContactWorkDao;
import com.example.omstugradebook.data.dao.ScheduleDao;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.dao.SearchScheduleDao;
import com.example.omstugradebook.data.dao.SubjectDao;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.contactwork.ContactWork;
import com.example.omstugradebook.data.model.grade.Subject;
import com.example.omstugradebook.data.model.grade.User;
import com.example.omstugradebook.data.model.schedule.Schedule;
import com.example.omstugradebook.data.model.schedule.ScheduleOwner;
import com.example.omstugradebook.data.model.schedule.SearchSchedule;

@Database(entities = {
        ScheduleOwner.class,
        User.class,
        Subject.class,
        Schedule.class,
        ContactWork.class,
        SearchSchedule.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduleOwnerDao getScheduleOwnerDao();

    public abstract UserDao getUserDao();

    public abstract SubjectDao getSubjectDao();

    public abstract ScheduleDao getScheduleDao();

    public abstract ContactWorkDao getContactWorkDao();

    public abstract SearchScheduleDao getSearchScheduleDao();
}
