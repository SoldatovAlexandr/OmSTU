package com.example.omstugradebook.recyclerview.holder.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.view.activity.LoginActivity;

public class InfoViewHolder extends AccountViewHolder implements View.OnClickListener {
    private CardView cardView;
    private Button button;

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.add_new_user_card_view);
        button = itemView.findViewById(R.id.add_new_user_button);
        button.setOnClickListener(this);
    }

    @Override
    public void draw(int position) {
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        v.getContext().startActivity(intent);
    }
}