package com.example.omstugradebook.presentation.recyclerview.holder.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.schedule.Schedule;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;
import com.example.omstugradebook.presentation.recyclerview.holder.schedule.content.ScheduleHolderContent;

public class ScheduleViewHolder extends AbstractScheduleHolder {
    private final TextView discipline;

    private final TextView timeStart;

    private final TextView timeEnd;

    private final TextView lecturer;

    private final TextView group;

    private final TextView auditorium;

    private final TextView kindOfWork;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        discipline = itemView.findViewById(R.id.schedule_discipline);

        timeStart = itemView.findViewById(R.id.schedule_time_start);

        timeEnd = itemView.findViewById(R.id.schedule_time_end);

        lecturer = itemView.findViewById(R.id.schedule_lecturer);

        group = itemView.findViewById(R.id.schedule_group);

        auditorium = itemView.findViewById(R.id.schedule_auditorium);

        kindOfWork = itemView.findViewById(R.id.schedule_kind_of_work);
    }

    @Override
    public void bind(HolderContent holderContent) {
        Schedule schedule = ((ScheduleHolderContent) holderContent).getSchedule();

        kindOfWork.setText(schedule.getKindOfWork());

        discipline.setText(schedule.getDiscipline());

        timeEnd.setText(schedule.getEndLesson());

        timeStart.setText(schedule.getBeginLesson());

        lecturer.setText(schedule.getLecturer());

        auditorium.setText(schedule.getAuditorium());

        group.setText(schedule.getStreamType());
    }
}
