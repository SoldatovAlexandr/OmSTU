package com.example.omstugradebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "OmSTU_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                + "type integer" + ");");

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
                + "stream text,"
                + "groups text,"
                + "subGroup text,"
                + "dayOfWeekString text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
