package com.example.omstugradebook.presentation.recyclerview.holder.contactwork;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.contactwork.ContactWork;
import com.example.omstugradebook.presentation.view.activity.view.ContactWorkTasksActivity;

public class ContactWorkViewHolder extends RecyclerView.ViewHolder {
    private final TextView discipline;

    private final TextView teacher;

    private final TextView numberOfTasks;

    public ContactWorkViewHolder(View itemView) {
        super(itemView);

        discipline = itemView.findViewById(R.id.contact_work_discipline);

        teacher = itemView.findViewById(R.id.contact_work_teacher);

        numberOfTasks = itemView.findViewById(R.id.contact_work_number_of_tasks);
    }

    public void bind(final ContactWork contactWork) {
        discipline.setText(contactWork.getDiscipline());

        teacher.setText(contactWork.getTeacher());

        numberOfTasks.setText(contactWork.getNumberOfTasks());

        this.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ContactWorkTasksActivity.class);

            intent.putExtra("path", contactWork.getTaskLink());

            intent.putExtra("discipline", contactWork.getDiscipline());

            v.getContext().startActivity(intent);
        });
    }

}
