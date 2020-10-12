package com.example.omstugradebook.view.activity;


import java.util.Calendar;

public interface CalendarProvider {
    void sendRequest(String requestType, Calendar calendar, String param);
}
