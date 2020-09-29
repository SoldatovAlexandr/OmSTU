package com.example.omstugradebook.recyclerview.holder.subject.content;

import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.recyclerview.holder.HolderContent;

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
