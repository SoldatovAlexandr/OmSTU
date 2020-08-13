package com.example.omstugradebook;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.model.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.GradeViewHolder> {

    private List<Subject> subjects;
    private static int size = 0;
    private Map<Integer, Integer> typeByPosition = new HashMap<>();
    private Map<Integer, Integer> subjectPositionByAbsolutePosition = new HashMap<>();
    private Map<Integer, Integer> titleByPosition = new HashMap<>();

    public RVAdapter(List<Subject> subjects) {
        setSubjects(subjects);
    }


    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        size = 0;
        int currentType = -1;
        typeByPosition.clear();
        titleByPosition.clear();
        subjectPositionByAbsolutePosition.clear();
        for (int i = 0; i < subjects.size(); i++) {
            Subject subject = subjects.get(i);
            if (currentType != subject.getType()) {
                currentType = subject.getType();
                typeByPosition.put(size, 1);
                titleByPosition.put(size, currentType);
                size++;
            }
            subjectPositionByAbsolutePosition.put(size, i);
            typeByPosition.put(size, 0);
            size++;
        }
        SubjectViewHolder.subjects = subjects;
    }

    @Override
    public int getItemViewType(int position) {
        return typeByPosition.get(position);
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card_view, parent, false);
                return new SubjectViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_view, parent, false);
                return new TitleViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card_view, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder gradeViewHolder, int i) {
        if (typeByPosition.get(i) == 0) {
            gradeViewHolder.draw(subjectPositionByAbsolutePosition.get(i));
        } else {
            gradeViewHolder.draw(titleByPosition.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_name);
        }

        protected void draw(int i) {
            String text = "";
            switch (i) {
                case 0:
                    text = "Экзамены";
                    break;
                case 1:
                    text = "Зачёты";
                    break;
                case 2:
                    text = "Практики";
                    break;
                case 3:
                    text = "Курсовые работы";
                    break;
                case 4:
                    text = "Дифференцированные зачеты";
                    break;
            }
            textView.setText(text);
        }
    }

    public static class TitleViewHolder extends GradeViewHolder {

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public static class SubjectViewHolder extends GradeViewHolder {
        private CardView cardView;
        private TextView subjectName;
        private TextView subjectGrade;
        private TextView subjectTeacher;
        private TextView subjectDate;
        private static List<Subject> subjects;

        SubjectViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectGrade = itemView.findViewById(R.id.subject_grade);
            subjectTeacher = itemView.findViewById(R.id.subject_teacher);
            subjectDate = itemView.findViewById(R.id.subject_date);
        }

        public TextView getSubjectDate() {
            return subjectDate;
        }

        public void setSubjectDate(TextView subjectDate) {
            this.subjectDate = subjectDate;
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }

        public TextView getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(TextView subjectName) {
            this.subjectName = subjectName;
        }

        public TextView getSubjectGrade() {
            return subjectGrade;
        }

        public void setSubjectGrade(TextView subjectGrade) {
            this.subjectGrade = subjectGrade;
        }

        public TextView getSubjectTeacher() {
            return subjectTeacher;
        }

        public void setSubjectTeacher(TextView subjectTeacher) {
            this.subjectTeacher = subjectTeacher;
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
                Resources resources = getCardView().getContext().getResources();
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
                getCardView().setCardBackgroundColor(color);
            }
        }
    }

}
