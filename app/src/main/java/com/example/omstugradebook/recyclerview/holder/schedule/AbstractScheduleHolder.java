package com.example.omstugradebook.recyclerview.holder.schedule;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.recyclerview.holder.HolderContent;

public abstract class AbstractScheduleHolder extends RecyclerView.ViewHolder {
    public AbstractScheduleHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(HolderContent holderContent);
}
