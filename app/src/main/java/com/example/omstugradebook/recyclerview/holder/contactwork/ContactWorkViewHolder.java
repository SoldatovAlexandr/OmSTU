package com.example.omstugradebook.recyclerview.holder.contactwork;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.view.activity.ContactWorkListActivity;

public class ContactWorkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CardView cardView;
    private TextView discipline;
    private TextView teacher;
    private TextView numberOfTasks;
    private ImageView imageView;
    private Context context;
    private ContactWork contactWork;


    public ContactWorkViewHolder(View itemView, Context context) {
        super(itemView);
        cardView = itemView.findViewById(R.id.contact_work_card_view);
        discipline = itemView.findViewById(R.id.contact_work_discipline);
        teacher = itemView.findViewById(R.id.contact_work_teacher);
        numberOfTasks = itemView.findViewById(R.id.contact_work_number_of_tasks);
        imageView = itemView.findViewById(R.id.contact_work_taskLink);
        this.context = context;
    }

    public void draw(ContactWork contactWork) {
        this.contactWork = contactWork;
        discipline.setText(contactWork.getDiscipline());
        teacher.setText(contactWork.getTeacher());
        numberOfTasks.setText(contactWork.getNumberOfTasks());
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ContactWorkListActivity.class);
        intent.putExtra("path", contactWork.getTaskLink());
        intent.putExtra("discipline", contactWork.getDiscipline());
        context.startActivity(intent);
    }
}
