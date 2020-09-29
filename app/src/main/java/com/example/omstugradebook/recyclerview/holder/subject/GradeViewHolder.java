package com.example.omstugradebook.recyclerview.holder.subject;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.recyclerview.holder.HolderContent;

public abstract class GradeViewHolder extends RecyclerView.ViewHolder {

    public GradeViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(HolderContent holderContent);
}