package com.example.omstugradebook.recyclerview.holder.subject;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.Subject;

import java.util.List;

public class SubjectViewHolder extends GradeViewHolder {
    private CardView cardView;
    private TextView subjectName;
    private TextView subjectGrade;
    private TextView subjectTeacher;
    private TextView subjectDate;
    private static List<Subject> subjects;

    public SubjectViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cv);
        subjectName = itemView.findViewById(R.id.subject_name);
        subjectGrade = itemView.findViewById(R.id.subject_grade);
        subjectTeacher = itemView.findViewById(R.id.subject_teacher);
        subjectDate = itemView.findViewById(R.id.subject_date);
    }

    public static void setSubjects(List<Subject> subjects) {
        SubjectViewHolder.subjects = subjects;
    }

    @Override
    public void draw(int i) {
        Subject subject = subjects.get(i);
        if (subject != null) {
            subjectName.setText(subject.getName());
            subjectGrade.setText(subject.getMark());
            subjectTeacher.setText(subject.getTeacher());
            subjectDate.setText(subject.getDate());
            int color;
            Resources resources = cardView.getContext().getResources();
            switch (subject.getMark()) {
                case "Зачтено":
                case "Отлично":
                    color = resources.getColor(R.color.colorGreen);
                    break;
                case "Хорошо":
                    color = resources.getColor(R.color.colorBlue);
                    break;
                case "Удовлетворительно":
                    color = resources.getColor(R.color.colorRed);
                    break;
                default:
                    color = resources.getColor(R.color.colorWhite);
                    break;
            }
            cardView.setCardBackgroundColor(color);
        }
    }
}