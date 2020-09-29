package com.example.omstugradebook.recyclerview.holder.subject.content;

import com.example.omstugradebook.recyclerview.holder.HolderContent;

public class TitleHolderContent extends HolderContent {
    private final String title;

    public TitleHolderContent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getType() {
        return 1;
    }
}
