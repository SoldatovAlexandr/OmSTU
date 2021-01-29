package com.example.omstugradebook.presentation.recyclerview.holder.subject;

import com.example.omstugradebook.data.model.grade.Subject;
import com.example.omstugradebook.data.model.grade.SubjectType;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.content.SubjectHolderContent;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.content.TitleHolderContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectContentHolderConverter {
    private final Map<Integer, HolderContent> contentHolderByPosition;

    public SubjectContentHolderConverter(final List<Subject> subjects) {
        contentHolderByPosition = new HashMap<>();

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
