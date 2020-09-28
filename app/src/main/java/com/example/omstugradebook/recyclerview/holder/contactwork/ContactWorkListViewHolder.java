package com.example.omstugradebook.recyclerview.holder.contactwork;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.service.ContactWorkService;

public class ContactWorkListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CardView cardView;
    private TextView comment;
    private TextView teacher;
    private TextView file;
    private TextView date;
    private TextView number;
    private ContactWorksTask contactWorksTask;
    private Context context;

    public ContactWorkListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        cardView = itemView.findViewById(R.id.contact_work_list_card_view);
        number = itemView.findViewById(R.id.contact_work_list_number);
        comment = itemView.findViewById(R.id.contact_work_list_comment);
        teacher = itemView.findViewById(R.id.contact_work_list_teacher);
        file = itemView.findViewById(R.id.contact_work_list_file);
        date = itemView.findViewById(R.id.contact_work_list_date);
    }

    public void draw(ContactWorksTask contactWorksTask) {
        this.contactWorksTask = contactWorksTask;
        number.setText(String.valueOf(contactWorksTask.getNumber()));
        comment.setText(contactWorksTask.getComment());
        teacher.setText(contactWorksTask.getTeacher());
        file.setText(contactWorksTask.getFile());
        date.setText(contactWorksTask.getDate());
        file.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContactWorkService contactWorkService = new ContactWorkService();
        contactWorkService.downloadFile(contactWorksTask.getLink(), contactWorksTask.getFile(), context);
    }
}
