package com.example.omstugradebook.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.Schedule;
import com.example.omstugradebook.recyclerview.holder.schedule.AbstractScheduleHolder;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleViewHolder;
import com.example.omstugradebook.recyclerview.holder.schedule.TitleViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleRVAdapter extends RecyclerView.Adapter<AbstractScheduleHolder> {
    private Map<Integer, Schedule> scheduleByPosition = new HashMap<>();
    private Map<Integer, String> titleTextByPosition = new HashMap<>();

    public ScheduleRVAdapter(List<Schedule> scheduleList) {
        setScheduleList(scheduleList);
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        scheduleByPosition.clear();
        titleTextByPosition.clear();
        int position = 0;
        int currentDay = 0;
        for (Schedule schedule : scheduleList) {
            if (schedule.getDayOfWeek() != currentDay) {
                currentDay = schedule.getDayOfWeek();
                titleTextByPosition.put(position, schedule.getDayOfWeekFullString() + ", " + schedule.getDate());
                position++;
            }
            scheduleByPosition.put(position, schedule);
            position++;
        }
        ScheduleViewHolder.setSchedules(scheduleByPosition);
        TitleViewHolder.setTitleByPosition(titleTextByPosition);

    }

    @Override
    public int getItemViewType(int position) {
        if (titleTextByPosition.containsKey(position)) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override

    public AbstractScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_view, parent, false);
                return new TitleViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card_view, parent, false);
                return new ScheduleViewHolder(view);
        }
        return new AbstractScheduleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractScheduleHolder holder, int position) {
        holder.draw(position);
    }

    @Override
    public int getItemCount() {
        return scheduleByPosition.size() + titleTextByPosition.size();
    }
}
