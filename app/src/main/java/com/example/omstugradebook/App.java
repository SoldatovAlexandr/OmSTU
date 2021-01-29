package com.example.omstugradebook;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.omstugradebook.data.database.AppDatabase;
import com.example.omstugradebook.presentation.module.AppComponent;
import com.example.omstugradebook.presentation.module.DaggerAppComponent;

public class App extends Application {
    private static Context instance;

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = getApplicationContext();

        component = DaggerAppComponent.create();

    }

    public static Context getContext() {
        return instance;
    }

    public static AppComponent getComponent() {
        return component;
    }
}
