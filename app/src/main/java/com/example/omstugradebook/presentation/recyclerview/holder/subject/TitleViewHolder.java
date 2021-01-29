package com.example.omstugradebook.presentation.recyclerview.holder.subject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;
import com.example.omstugradebook.presentation.recyclerview.holder.HolderContent;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.content.TitleHolderContent;

public class TitleViewHolder extends GradeViewHolder {
    private final TextView textView;

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.title_name);
    }

    @Override
    public void bind(HolderContent holderContent) {
        TitleHolderContent titleHolderContent = ((TitleHolderContent) holderContent);

        textView.setText(titleHolderContent.getTitle());
    }
}
