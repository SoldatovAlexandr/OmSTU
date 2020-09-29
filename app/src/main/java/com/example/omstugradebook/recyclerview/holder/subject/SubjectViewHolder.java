package com.example.omstugradebook.recyclerview.holder.subject;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.subject.content.SubjectHolderContent;

public class SubjectViewHolder extends GradeViewHolder {
    private final CardView cardView;
    private final TextView subjectName;
    private final TextView subjectGrade;
    private final TextView subjectTeacher;
    private final TextView subjectDate;

    public SubjectViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cv);
        subjectName = itemView.findViewById(R.id.subject_name);
        subjectGrade = itemView.findViewById(R.id.subject_grade);
        subjectTeacher = itemView.findViewById(R.id.subject_teacher);
        subjectDate = itemView.findViewById(R.id.subject_date);
    }


    @Override
    public void bind(HolderContent holderContent) {
        Subject subject = ((SubjectHolderContent) holderContent).getSubject();
        if (subject != null) {
            subjectName.setText(subject.getName());
            subjectGrade.setText(subject.getMark());
            subjectTeacher.setText(subject.getTeacher());
            subjectDate.setText(subject.getDate());
            cardView.setCardBackgroundColor(getColor(subject));
        }
    }

    private int getColor(Subject subject) {
        Resources resources = cardView.getContext().getResources();
        switch (subject.getMark()) {
            case "Зачтено":
            case "Отлично":
                return resources.getColor(R.color.colorGreen);
            case "Хорошо":
                return resources.getColor(R.color.colorBlue);
            case "Удовлетворительно":
                return resources.getColor(R.color.colorRed);
            default:
                return resources.getColor(R.color.colorWhite);
        }
    }
}