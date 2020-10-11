package com.example.omstugradebook.dto;

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
        switch (dayOfWeek) {
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
        }
        return "Воскресенье";
    }
}
