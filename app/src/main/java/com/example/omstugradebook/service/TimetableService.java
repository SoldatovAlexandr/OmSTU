package com.example.omstugradebook.service;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.dto.GetIdForTypeDtoResponse;
import com.example.omstugradebook.dto.ScheduleConverter;
import com.example.omstugradebook.dto.ScheduleDtoResponse;
import com.example.omstugradebook.model.schedule.Schedule;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private ScheduleConverter builder = new ScheduleConverter();

    public List<Schedule> getTimetable(String type, String param, String start, String finish, int lang) {
        String requestParam = getRequestParam(param, start, finish, lang);
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            String API_URL = "https://rasp.omgtu.ru/api/schedule/" + type + "/";
            String response = Jsoup.connect(API_URL + requestParam)
                    .ignoreContentType(true).get().text();
            Gson gson = new Gson();
            ScheduleDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleDtoResponse[].class);
            for (ScheduleDtoResponse dtoResponse : dtoResponses) {
                scheduleList.add(builder.convert(dtoResponse,
                        DataBaseManager.getUserDao().getActiveUser().getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    private String getRequestParam(String param, String start, String finish, int lang) {
        return getIdByParam(param) + "?start=" + start + "&finish=" + finish + "&lng=" + lang;
    }

    public int getIdByParam(String param) {
        try {
            String response = Jsoup.connect("https://rasp.omgtu.ru/api/search?term=" + param.trim())
                    .ignoreContentType(true).get().text();
            Gson gson = new Gson();
            GetIdForTypeDtoResponse[] dtoResponses = gson.fromJson(response, GetIdForTypeDtoResponse[].class);
            if (dtoResponses.length > 0) {
                GetIdForTypeDtoResponse groupDtoResponse = dtoResponses[0];
                return groupDtoResponse.getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
