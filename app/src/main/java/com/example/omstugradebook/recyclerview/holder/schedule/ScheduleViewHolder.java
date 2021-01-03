package com.example.omstugradebook.recyclerview.holder.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.schedule.Schedule;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.schedule.content.ScheduleHolderContent;

public class ScheduleViewHolder extends AbstractScheduleHolder {
    private final TextView discipline;

    private final TextView timeStart;

    private final TextView timeEnd;

    private final TextView lecturer;

    private final TextView group;

    private final TextView auditorium;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        discipline = itemView.findViewById(R.id.schedule_discipline);

        timeStart = itemView.findViewById(R.id.schedule_time_start);

        timeEnd = itemView.findViewById(R.id.schedule_time_end);

        lecturer = itemView.findViewById(R.id.schedule_lecturer);

        group = itemView.findViewById(R.id.schedule_group);

        auditorium = itemView.findViewById(R.id.schedule_auditorium);
    }

    @Override
    public void bind(HolderContent holderContent) {
        Schedule schedule = ((ScheduleHolderContent) holderContent).getSchedule();

        discipline.setText(schedule.getDiscipline());

        timeEnd.setText(schedule.getEndLesson());

        timeStart.setText(schedule.getBeginLesson());

        lecturer.setText(schedule.getLecturer());

        auditorium.setText(schedule.getAuditorium());

        group.setText(schedule.getStreamType());
    }
}
