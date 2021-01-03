package com.example.omstugradebook.recyclerview.holder.schedule;

import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.schedule.content.ScheduleHolderContent;
import com.example.omstugradebook.recyclerview.holder.schedule.content.TitleHolderContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleContentHolderConverter {
    private final List<Schedule> scheduleList;

    public ScheduleContentHolderConverter(final List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public Map<Integer, HolderContent> getContent() {
        final Map<Integer, HolderContent> contentHolderByPosition = new HashMap<>();

        int position = 0;

        int currentDay = 0;

        for (Schedule schedule : scheduleList) {
            if (schedule.getDayOfWeek() != currentDay) {
                currentDay = schedule.getDayOfWeek();

                String title = schedule.getDayOfWeekString() + ", " + schedule.getDate();

                contentHolderByPosition.put(position, new TitleHolderContent(title));

                position++;
            }
            contentHolderByPosition.put(position, new ScheduleHolderContent(schedule));

            position++;
        }

        return contentHolderByPosition;
    }
}
