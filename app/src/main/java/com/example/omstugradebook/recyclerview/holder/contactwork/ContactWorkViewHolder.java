package com.example.omstugradebook.recyclerview.holder.contactwork;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.view.activity.ContactWorkListActivity;

public class ContactWorkViewHolder extends RecyclerView.ViewHolder {
    private TextView discipline;
    private TextView teacher;
    private TextView numberOfTasks;
    private ImageView imageView;

    public ContactWorkViewHolder(View itemView) {
        super(itemView);
        discipline = itemView.findViewById(R.id.contact_work_discipline);
        teacher = itemView.findViewById(R.id.contact_work_teacher);
        numberOfTasks = itemView.findViewById(R.id.contact_work_number_of_tasks);
        imageView = itemView.findViewById(R.id.contact_work_taskLink);
    }

    public void bind(final ContactWork contactWork) {
        discipline.setText(contactWork.getDiscipline());
        teacher.setText(contactWork.getTeacher());
        numberOfTasks.setText(contactWork.getNumberOfTasks());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactWorkListActivity.class);
                intent.putExtra("path", contactWork.getTaskLink());
                intent.putExtra("discipline", contactWork.getDiscipline());
                v.getContext().startActivity(intent);
            }
        });
    }

}
