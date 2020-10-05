package com.example.omstugradebook.service;

import com.example.omstugradebook.dto.GroupDtoResponse;
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
    private int groupNumber = -1;

    public List<Schedule> getTimetable(String nameGroup, String start, String finish, int lang) {
        String requestParam = getRequestParam(nameGroup, start, finish, lang);
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            String API_URL = "https://rasp.omgtu.ru/api/schedule/group/";
            String response = Jsoup.connect(API_URL + requestParam)
                    .ignoreContentType(true).get().text();
            Gson gson = new Gson();
            ScheduleDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleDtoResponse[].class);
            for (ScheduleDtoResponse dtoResponse : dtoResponses) {
                scheduleList.add(builder.convert(dtoResponse));
            }
            System.out.println(scheduleList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    private String getRequestParam(String nameGroup, String start, String finish, int lang) {
        return getGroupNumber(nameGroup) + "?start=" + start + "&finish=" + finish + "&lng=" + lang;
    }

    private int getGroupNumber(String nameGroup) {
        if (groupNumber != -1) {
            return groupNumber;
        }
        return updateGroupNumber(nameGroup);
    }

    public int updateGroupNumber(String nameGroup) {
        try {
            String response = Jsoup.connect("https://rasp.omgtu.ru/api/search?term="
                    + nameGroup + "&type=group").ignoreContentType(true).get().text();
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
