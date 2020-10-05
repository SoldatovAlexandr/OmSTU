package com.example.omstugradebook.recyclerview.holder.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.view.activity.view.LoginActivity;

public class InfoViewHolder extends AccountViewHolder {

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        final Button button = itemView.findViewById(R.id.add_new_user_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public void bind(int position, User user) {
    }

}