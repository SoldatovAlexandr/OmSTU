package com.example.omstugradebook.recyclerview.adapter;

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
    private final List<ContactWorksTask> contactWorksTasksList = new ArrayList<>();
    private final View.OnClickListener listener;

    public ContactWorkListRVAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setContactWorksTasksList(List<ContactWorksTask> contactWorksTasksList) {
        this.contactWorksTasksList.clear();
        this.contactWorksTasksList.addAll(contactWorksTasksList);
    }

    public List<ContactWorksTask> getContactWorksTasksList() {
        return contactWorksTasksList;
    }

    @NonNull
    @Override
    public ContactWorkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_work_list_card, parent, false);
        return new ContactWorkListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactWorkListViewHolder holder, int position) {
        holder.bind(contactWorksTasksList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contactWorksTasksList.size();
    }
}
