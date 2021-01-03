package com.example.omstugradebook.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.schedule.AbstractScheduleHolder;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleContentHolderConverter;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleHolderType;
import com.example.omstugradebook.recyclerview.holder.schedule.ScheduleViewHolder;
import com.example.omstugradebook.recyclerview.holder.schedule.TitleViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ScheduleRVAdapter extends RecyclerView.Adapter<AbstractScheduleHolder> {
    private final Map<Integer, HolderContent> contentHolderByPosition;

    public ScheduleRVAdapter() {
        contentHolderByPosition = new HashMap<>();
    }

    public void setScheduleList(ScheduleContentHolderConverter scc) {
        contentHolderByPosition.clear();
        contentHolderByPosition.putAll(scc.getContent());
    }

    @Override
    public int getItemViewType(int position) {
        return contentHolderByPosition.get(position).getType();
    }

    @NonNull
    @Override
    public AbstractScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        ScheduleHolderType scheduleHolderType = ScheduleHolderType.values()[viewType];

        switch (scheduleHolderType) {
            case TITLE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.title_view, parent, false);

                return new TitleViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.schedule_card_view, parent, false);

                return new ScheduleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractScheduleHolder holder, int position) {
        holder.bind(contentHolderByPosition.get(position));
    }

    @Override
    public int getItemCount() {
        return contentHolderByPosition.size();
    }
}
