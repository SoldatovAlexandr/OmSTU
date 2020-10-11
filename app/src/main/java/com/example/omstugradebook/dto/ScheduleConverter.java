package com.example.omstugradebook.dto;

import com.example.omstugradebook.model.schedule.DayOfWeek;
import com.example.omstugradebook.model.schedule.Schedule;

public class ScheduleConverter {
    public Schedule convert(ScheduleDtoResponse response, long userId) {
        return new Schedule(response.getAuditorium(),
                response.getBeginLesson(),
                response.getBuilding(),
                response.getDate(),
                response.getDayOfWeek(),
                response.getDetailInfo(),
                response.getDiscipline(),
                response.getEndLesson(),
                response.getKindOfWork(),
                response.getLecturer(),
                getStreamType(response),
                getDayOfWeekFullString(response.getDayOfWeek()),
                userId);
    }

    private String getStreamType(ScheduleDtoResponse response) {
        if (response.getGroup() != null) {
            return response.getGroup();
        } else if (response.getSubGroup() != null) {
            return response.getSubGroup();
        } else return response.getStream();
    }

    public String getDayOfWeekFullString(int dayOfWeek) {
        return DayOfWeek.values()[dayOfWeek - 1].getName();
    }
}
