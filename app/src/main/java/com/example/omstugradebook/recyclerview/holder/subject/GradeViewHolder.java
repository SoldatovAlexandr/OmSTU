package com.example.omstugradebook.recyclerview.holder.subject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.SubjectType;

public class GradeViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public GradeViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title_name);
    }

    public void draw(int i) {
        SubjectType subjectType = SubjectType.values()[i];
        String text = "";
        switch (subjectType) {
            case EXAM:
                text = "Экзамены";
                break;
            case TEST:
                text = "Зачёты";
                break;
            case PRACTICE:
                text = "Практики";
                break;
            case COURSE_WORK:
                text = "Курсовые работы";
                break;
            case DIF_TEST:
                text = "Дифференцированные зачеты";
                break;
        }
        textView.setText(text);
    }
}