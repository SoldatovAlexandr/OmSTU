package com.example.omstugradebook.dto;

import com.example.omstugradebook.model.schedule.Schedule;

public class ScheduleConverter {
    public Schedule convert(ScheduleDtoResponse response) {
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
                response.getDayOfWeekString());
    }

    private String getStreamType(ScheduleDtoResponse response) {
        if (response.getGroup() != null) {
            return response.getGroup();
        } else if (response.getSubGroup() != null) {
            return response.getSubGroup();
        } else return response.getStream();
    }
}
