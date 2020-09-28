package com.example.omstugradebook.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.recyclerview.holder.contactwork.ContactWorkListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ContactWorkListRVAdapter extends RecyclerView.Adapter<ContactWorkListViewHolder> {
    private List<ContactWorksTask> contactWorksTasksList;
    private Context context;

    public ContactWorkListRVAdapter(Context context) {
        contactWorksTasksList = new ArrayList<>();
        this.context = context;
    }

    public void setContactWorksTasksList(List<ContactWorksTask> contactWorksTasksList) {
        this.contactWorksTasksList = contactWorksTasksList;
    }

    @NonNull
    @Override
    public ContactWorkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_work_list_card, parent, false);
        return new ContactWorkListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactWorkListViewHolder holder, int position) {
        holder.draw(contactWorksTasksList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactWorksTasksList.size();
    }
}
