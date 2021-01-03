package com.example.omstugradebook.service;

import com.example.omstugradebook.App;
import com.example.omstugradebook.dao.ScheduleDao;
import com.example.omstugradebook.dao.ScheduleOwnerDao;
import com.example.omstugradebook.dto.ScheduleDtoResponse;
import com.example.omstugradebook.dto.ScheduleMapper;
import com.example.omstugradebook.dto.ScheduleOwnersDtoResponse;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ScheduleService {
    private final ScheduleMapper builder;

    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    public ScheduleService() {
        App.getComponent().injectScheduleService(this);

        builder = new ScheduleMapper();
    }

    public List<Schedule> getTimetable(String id, String start, String finish, String type, int lang) {
        String requestParam = getRequestParam(id, start, finish, type, lang);

        List<Schedule> scheduleList = new ArrayList<>();

        try {
            String API_URL = "https://rasp.omgtu.ru/api/schedule/";

            String requestString = API_URL + requestParam;

            String response = Jsoup.connect(requestString).ignoreContentType(true).get().text();

            Gson gson = new Gson();

            ScheduleDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleDtoResponse[].class);

            for (ScheduleDtoResponse dtoResponse : dtoResponses) {
                scheduleList.add(builder.map(dtoResponse));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    private String getRequestParam(String id, String start, String finish, String type, int lang) {
        return type + "/" + id + "?start=" + start + "&finish=" + finish + "&lng=" + lang;
    }


    public List<ScheduleOwner> getScheduleOwners(String param, boolean hasInternet) {
        if (hasInternet) {
            try {
                String response = Jsoup.connect("https://rasp.omgtu.ru/api/search?term=" + param.trim())
                        .ignoreContentType(true).get().text();

                Gson gson = new Gson();

                ScheduleOwnersDtoResponse[] dtoResponses = gson.fromJson(response, ScheduleOwnersDtoResponse[].class);

                return convert(dtoResponses);
            } catch (IOException e) {
                e.printStackTrace();

                return scheduleOwnerDao.getAll();
            }
        } else {
            return scheduleOwnerDao.getAll();
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
