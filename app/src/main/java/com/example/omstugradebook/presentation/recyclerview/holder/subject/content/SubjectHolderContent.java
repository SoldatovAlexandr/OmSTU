package com.example.omstugradebook.presentation.recyclerview.holder.subject.content;

import com.example.omstugradebook.data.model.grade.Subject;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;

public class SubjectHolderContent extends HolderContent {
    private final Subject subject;

    public SubjectHolderContent(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int getType() {
        return 0;
    }

    public Subject getSubject() {
        return subject;
    }
}
