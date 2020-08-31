package com.example.omstugradebook.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.SubjectType;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.recyclerview.holder.subject.GradeViewHolder;
import com.example.omstugradebook.recyclerview.holder.subject.SubjectViewHolder;
import com.example.omstugradebook.recyclerview.holder.subject.TitleViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeRVAdapter extends RecyclerView.Adapter<GradeViewHolder> {

    private static int size = 0;
    private Map<Integer, Integer> typeByPosition = new HashMap<>();
    private Map<Integer, Integer> subjectPositionByAbsolutePosition = new HashMap<>();
    private Map<Integer, Integer> titleByPosition = new HashMap<>();

    public GradeRVAdapter(List<Subject> subjects) {
        setSubjects(subjects);
    }


    public void setSubjects(List<Subject> subjects) {
        size = 0;
        SubjectType currentType = null;
        typeByPosition.clear();
        titleByPosition.clear();
        subjectPositionByAbsolutePosition.clear();
        for (int i = 0; i < subjects.size(); i++) {
            Subject subject = subjects.get(i);
            if (currentType != subject.getType()) {
                currentType = subject.getType();
                typeByPosition.put(size, 1);
                titleByPosition.put(size, currentType.ordinal());
                size++;
            }
            subjectPositionByAbsolutePosition.put(size, i);
            typeByPosition.put(size, 0);
            size++;
        }
        SubjectViewHolder.setSubjects(subjects);
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
}
