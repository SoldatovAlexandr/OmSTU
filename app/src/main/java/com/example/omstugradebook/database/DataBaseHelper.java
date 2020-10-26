package com.example.omstugradebook.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omstugradebook.OmSTUApplication;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper() {
        super(OmSTUApplication.getContext(), "OmSTU_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON;");

        db.execSQL("create table users ("
                + "id integer primary key autoincrement,"
                + "login text,"
                + "token text,"
                + "fullName text,"
                + "numberGradeBook text,"
                + "speciality text,"
                + "educationForm text,"
                + "isActive integer,"
                + "password text" + ");");

        db.execSQL("create table contact_work ("
                + "id integer primary key autoincrement,"
                + "discipline text,"
                + "teacher text,"
                + "number_of_tasks text,"
                + "task_link text"
                + ");");

        db.execSQL("create table subjects ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "hours text,"
                + "attendance text,"
                + "tempRating text,"
                + "mark text,"
                + "date text,"
                + "teacher text,"
                + "toDiploma text,"
                + "term integer,"
                + "user_id integer,"
                + "type integer,"
                + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" + ");");


        db.execSQL("create table schedules ("
                + "id integer primary key autoincrement,"
                + "auditorium text,"
                + "beginLesson text,"
                + "building text,"
                + "date text,"
                + "dayOfWeek integer,"
                + "detailInfo text,"
                + "discipline text,"
                + "endLesson text,"
                + "kindOfWork text,"
                + "lecturer text,"
                + "streamType text,"
                + "user_id integer,"
                + "dayOfWeekString text,"
                + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" + ");");

        db.execSQL("create table favorite_schedule ("
                + "id integer primary key autoincrement,"
                + "user_id integer,"
                + "value text," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
