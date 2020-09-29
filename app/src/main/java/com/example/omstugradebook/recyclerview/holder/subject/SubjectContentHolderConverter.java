package com.example.omstugradebook.recyclerview.holder.subject;

import com.example.omstugradebook.SubjectType;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.subject.content.SubjectHolderContent;
import com.example.omstugradebook.recyclerview.holder.subject.content.TitleHolderContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectContentHolderConverter {
    private final Map<Integer, HolderContent> contentHolderByPosition = new HashMap<>();

    public SubjectContentHolderConverter(final List<Subject> subjects) {
        int size = 0;
        SubjectType currentType = null;
        for (int i = 0; i < subjects.size(); i++) {
            Subject subject = subjects.get(i);
            if (currentType != subject.getType()) {
                currentType = subject.getType();
                contentHolderByPosition.put(size, new TitleHolderContent(currentType.getTextString()));
                size++;
            }
            contentHolderByPosition.put(size, new SubjectHolderContent(subject));
            size++;
        }
    }

    public Map<Integer, HolderContent> getContentHolderByPosition() {
        return contentHolderByPosition;
    }
}
