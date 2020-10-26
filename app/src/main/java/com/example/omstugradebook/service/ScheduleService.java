package com.example.omstugradebook.service;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.dto.ScheduleConverter;
import com.example.omstugradebook.dto.ScheduleDtoResponse;
import com.example.omstugradebook.dto.ScheduleOwnersDtoResponse;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    private ScheduleConverter builder = new ScheduleConverter();

    public List<Schedule> getTimetable(String id, String start, String finish, String type, int lang) {
        String requestParam = getRequestParam(id, start, finish, type, lang);
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            String API_URL = "https://rasp.omgtu.ru/api/schedule/";
            String response = Jsoup.connect(API_URL + requestParam).ignoreContentType(true).get().text();
            Gson gson = new Gson();
            ScheduleDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleDtoResponse[].class);
            for (ScheduleDtoResponse dtoResponse : dtoResponses) {
                scheduleList.add(builder.convert(dtoResponse));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    private String getRequestParam(String id, String start, String finish, String type, int lang) {
        return type + "/" + id + "?start=" + start + "&finish=" + finish + "&lng=" + lang;
    }


    public List<ScheduleOwner> getScheduleOwners(String param) {
        try {
            String response = Jsoup.connect("https://rasp.omgtu.ru/api/search?term=" + param.trim())
                    .ignoreContentType(true).get().text();
            Gson gson = new Gson();
            ScheduleOwnersDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleOwnersDtoResponse[].class);
            return convert(dtoResponses);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<ScheduleOwner> convert(ScheduleOwnersDtoResponse[] scheduleOwnersDtoResponses) {
        List<ScheduleOwner> scheduleOwners = new ArrayList<>();
        for (ScheduleOwnersDtoResponse dtoResponse : scheduleOwnersDtoResponses) {
            scheduleOwners.add(new ScheduleOwner(dtoResponse.getId(),
                    dtoResponse.getLabel(),
                    dtoResponse.getType()));
        }
        return scheduleOwners;
    }

}
