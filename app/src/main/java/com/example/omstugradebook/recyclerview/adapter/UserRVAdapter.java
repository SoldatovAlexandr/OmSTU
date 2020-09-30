package com.example.omstugradebook.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.recyclerview.holder.user.AccountViewHolder;
import com.example.omstugradebook.recyclerview.holder.user.InfoViewHolder;
import com.example.omstugradebook.recyclerview.holder.user.UserViewHolder;
import com.example.omstugradebook.view.fragments.Updatable;

import java.util.ArrayList;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    private final List<User> users = new ArrayList<>();
    private final Updatable updatable;

    public UserRVAdapter(Updatable updatable) {
        this.updatable = updatable;
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
                return new UserViewHolder(view, updatable);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_user_card_view, parent, false);
                return new InfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(position, users.size() == position ? null : users.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position < users.size() ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return users.size() + 1;
    }
}
