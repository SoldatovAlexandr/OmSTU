package com.example.omstugradebook.presentation.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.GradeViewHolder;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.SubjectContentHolderConverter;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.SubjectViewHolder;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.TitleViewHolder;

import java.util.HashMap;
import java.util.Map;

public class GradeRVAdapter extends RecyclerView.Adapter<GradeViewHolder> {
    private final Map<Integer, HolderContent> contentHolderByPosition;

    public GradeRVAdapter() {
        this.contentHolderByPosition = new HashMap<>();
    }

    public void setSubjects(final SubjectContentHolderConverter subjectContentHolderConverter) {
        this.contentHolderByPosition.clear();

        this.contentHolderByPosition.putAll(subjectContentHolderConverter.getContentHolderByPosition());
    }

    @Override
    public int getItemViewType(int position) {
        return contentHolderByPosition.get(position).getType();
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.subject_card_view, parent, false);

                return new SubjectViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.title_view, parent, false);

                return new TitleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(GradeViewHolder gradeViewHolder, int i) {
        gradeViewHolder.bind(contentHolderByPosition.get(i));
    }

    @Override
    public int getItemCount() {
        return contentHolderByPosition.size();
    }

}
