package com.example.omstugradebook.recyclerview.holder.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omstugradebook.R;

import java.util.Map;

public class TitleViewHolder extends AbstractScheduleHolder {
    private TextView textView;
    private static Map<Integer, String> titleByPosition;

    public static void setTitleByPosition(Map<Integer, String> titleByPosition) {
        TitleViewHolder.titleByPosition = titleByPosition;
    }

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title_name);
    }

    @Override
    public void draw(int position) {
        super.draw(position);
        textView.setText(titleByPosition.get(position));
    }
}
