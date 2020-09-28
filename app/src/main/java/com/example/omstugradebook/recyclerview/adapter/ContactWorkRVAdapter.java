package com.example.omstugradebook.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.recyclerview.holder.contactwork.ContactWorkViewHolder;

import java.util.List;

public class ContactWorkRVAdapter extends RecyclerView.Adapter<ContactWorkViewHolder> {
    private List<ContactWork> contactWorks;
    private Context context;

    public ContactWorkRVAdapter(@NonNull List<ContactWork> contactWorks, @NonNull Context context) {
        setContactWorks(contactWorks, context);
    }

    public void setContactWorks(List<ContactWork> contactWorks, Context context) {
        this.contactWorks = contactWorks;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_work_card, parent, false);
        return new ContactWorkViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactWorkViewHolder holder, int position) {
        holder.draw(contactWorks.get(position));
    }

    @Override
    public int getItemCount() {
        return contactWorks.size();
    }
}
