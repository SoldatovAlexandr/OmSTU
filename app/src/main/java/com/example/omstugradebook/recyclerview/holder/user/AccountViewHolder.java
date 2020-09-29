package com.example.omstugradebook.recyclerview.holder.user;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.model.grade.User;

public abstract class AccountViewHolder extends RecyclerView.ViewHolder {

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(int position, User user);
}