package com.example.omstugradebook.recyclerview.holder.subject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;

public class GradeViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public GradeViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title_name);
    }

    public void draw(int i) {
        String text = "";
        switch (i) {
            case 0:
                text = "Экзамены";
                break;
            case 1:
                text = "Зачёты";
                break;
            case 3:
                text = "Практики";
                break;
            case 2:
                text = "Курсовые работы";
                break;
            case 4:
                text = "Дифференцированные зачеты";
                break;
        }
        textView.setText(text);
    }
}