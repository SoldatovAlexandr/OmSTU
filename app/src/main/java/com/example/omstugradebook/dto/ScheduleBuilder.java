package com.example.omstugradebook.dto;

import com.example.omstugradebook.model.Schedule;

public class ScheduleBuilder {
    public Schedule build(ScheduleDtoResponse response) {
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
                response.getStream(),
                response.getGroup(),
                response.getSubGroup(),
                response.getDayOfWeekString());
    }
}
