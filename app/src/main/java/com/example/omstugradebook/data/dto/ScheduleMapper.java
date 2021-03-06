package com.example.omstugradebook.data.dto;

import com.example.omstugradebook.data.model.schedule.DayOfWeek;
import com.example.omstugradebook.data.model.schedule.Schedule;

public class ScheduleMapper {
    public Schedule map(ScheduleDtoResponse response) {
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
                getDayOfWeekFullString(response.getDayOfWeek()));
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
