package com.example.omstugradebook.service;

import android.annotation.SuppressLint;

import com.example.omstugradebook.dto.DtoResponseException;
import com.example.omstugradebook.dto.DtoValidator;
import com.example.omstugradebook.dto.GroupDtoResponse;
import com.example.omstugradebook.dto.ScheduleBuilder;
import com.example.omstugradebook.dto.ScheduleDtoResponse;
import com.example.omstugradebook.model.Schedule;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimetableService {
    private final String API_URL = "https://rasp.omgtu.ru/api/schedule/group/";
    private DtoValidator validator = new DtoValidator();
    private ScheduleBuilder builder = new ScheduleBuilder();
    private int groupNumber = -1;

    public List<Schedule> getTimetable(String nameGroup, Calendar start, Calendar finish, int lang) {
        String requestParam = getRequestParam(nameGroup, start, finish, lang);
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            String response = Jsoup.connect(API_URL + requestParam).ignoreContentType(true).get().text();
            Gson gson = new Gson();
            ScheduleDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleDtoResponse[].class);
            for (ScheduleDtoResponse dtoResponse : dtoResponses) {
                validator.validate(dtoResponse);
                scheduleList.add(builder.build(dtoResponse));
            }
            System.out.println(scheduleList);
        } catch (IOException | DtoResponseException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    private String getRequestParam(String nameGroup, Calendar start, Calendar finish, int lang) {
        return getGroupNumber(nameGroup) + "?start=" + getDateString(start) + "&finish=" + getDateString(finish) + "&lng=" + lang;
    }

    @SuppressLint("SimpleDateFormat")
    private String getDateString(Calendar calendar) {
        return new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime());
    }

    private int getGroupNumber(String nameGroup) {
        if (groupNumber != -1) {
            return groupNumber;
        }
        return updateGroupNumber(nameGroup);
    }

    public int updateGroupNumber(String nameGroup) {
        try {
            String response = Jsoup.connect("https://rasp.omgtu.ru/api/search?term=" + nameGroup + "&type=group").ignoreContentType(true).get().text();
            Gson gson = new Gson();
            GroupDtoResponse[] dtoResponses = gson.fromJson(response, GroupDtoResponse[].class);
            if (dtoResponses.length > 0) {
                GroupDtoResponse groupDtoResponse = dtoResponses[0];
                groupNumber = groupDtoResponse.getId();
                return groupNumber;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
