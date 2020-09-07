package com.example.omstugradebook.recyclerview.holder.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.Schedule;

import java.util.List;
import java.util.Map;

public class ScheduleViewHolder extends AbstractScheduleHolder {
    private CardView cardView;
    private TextView discipline;
    private TextView timeStart;
    private TextView timeEnd;
    private TextView lecturer;
    private TextView group;
    private static Map<Integer, Schedule> schedules;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.schedule_card_view);
        discipline = itemView.findViewById(R.id.schedule_discipline);
        timeStart = itemView.findViewById(R.id.schedule_time_start);
        timeEnd = itemView.findViewById(R.id.schedule_time_end);
        lecturer = itemView.findViewById(R.id.schedule_lecturer);
        group = itemView.findViewById(R.id.schedule_group);
    }

    public static void setSchedules(Map<Integer, Schedule> schedules) {
        ScheduleViewHolder.schedules = schedules;
    }

    @Override
    public void draw(int position) {
        Schedule schedule = schedules.get(position);
        discipline.setText(schedule.getDiscipline());
        timeEnd.setText(schedule.getEndLesson());
        timeStart.setText(schedule.getBeginLesson());
        lecturer.setText(schedule.getLecturer());
        String groupString = "";
        if (schedule.getGroup() != null) {
            groupString = schedule.getGroup();
        } else if (schedule.getSubGroup() != null) {
            groupString = schedule.getSubGroup();
        } else if (schedule.getStream() != null) {
            groupString = schedule.getStream();
        }
        group.setText(groupString);
    }
}
