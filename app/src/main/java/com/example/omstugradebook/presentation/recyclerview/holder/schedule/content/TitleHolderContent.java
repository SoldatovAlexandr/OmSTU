package com.example.omstugradebook.presentation.recyclerview.holder.schedule.content;

import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;

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
        return 0;
    }
}
