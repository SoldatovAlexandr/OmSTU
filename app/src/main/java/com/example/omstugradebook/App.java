package com.example.omstugradebook;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.omstugradebook.database.AppDatabase;
import com.example.omstugradebook.module.AppComponent;
import com.example.omstugradebook.module.DaggerAppComponent;

public class App extends Application {
    private static Context instance;

    private static AppComponent component;

    private static AppDatabase database;

    private static final String DATABASE_NAME = "populus-database";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = getApplicationContext();

        component = DaggerAppComponent.create();

        database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME).build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    public static Context getContext() {
        return instance;
    }

    public static AppComponent getComponent() {
        return component;
    }
}
