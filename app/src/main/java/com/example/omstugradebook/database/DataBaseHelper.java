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
                + "type integer" + ");");

        db.execSQL("create table users ("
                + "id integer primary key autoincrement,"
                + "login text,"
                + "token text,"
                + "password text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
