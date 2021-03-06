package com.example.omstugradebook.presentation.recyclerview.holder.contactwork;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.contactwork.ContactWorksTask;

public class ContactWorkListViewHolder extends RecyclerView.ViewHolder {
    private final TextView comment;

    private final TextView teacher;

    private final Button file;

    private final TextView date;

    private final TextView number;


    public ContactWorkListViewHolder(@NonNull View itemView) {
        super(itemView);

        number = itemView.findViewById(R.id.contact_work_list_number);

        comment = itemView.findViewById(R.id.contact_work_list_comment);

        teacher = itemView.findViewById(R.id.contact_work_list_teacher);

        file = itemView.findViewById(R.id.contact_work_list_file);

        date = itemView.findViewById(R.id.contact_work_list_date);
    }

    public void bind(final ContactWorksTask contactWorksTask, final View.OnClickListener listener) {
        number.setText(String.valueOf(contactWorksTask.getNumber()));

        comment.setText(contactWorksTask.getComment());

        teacher.setText(contactWorksTask.getTeacher());

        file.setText(contactWorksTask.getFile());

        date.setText(contactWorksTask.getDate());

        file.setOnClickListener(listener);
    }
}
