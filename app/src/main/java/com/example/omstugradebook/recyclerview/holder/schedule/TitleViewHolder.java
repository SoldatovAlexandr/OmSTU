package com.example.omstugradebook.recyclerview.holder.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;
import com.example.omstugradebook.recyclerview.holder.HolderContent;
import com.example.omstugradebook.recyclerview.holder.schedule.content.TitleHolderContent;

public class TitleViewHolder extends AbstractScheduleHolder {
    private final TextView textView;

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title_name);
    }

    @Override
    public void bind(HolderContent holderContent) {
        textView.setText(((TitleHolderContent) holderContent).getTitle());
    }
}
