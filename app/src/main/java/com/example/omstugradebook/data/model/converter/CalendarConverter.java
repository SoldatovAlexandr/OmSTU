package com.example.omstugradebook.data.model.converter;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarConverter {


    @TypeConverter
    public long fromCalendar(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public Calendar toCalendar(long millis) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}
