package com.example.omstugradebook.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.User;
import com.example.omstugradebook.recyclerview.holder.user.AccountViewHolder;
import com.example.omstugradebook.recyclerview.holder.user.InfoViewHolder;
import com.example.omstugradebook.recyclerview.holder.user.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    private static List<User> users;
    private Context context;

    public static List<ImageButton> buttons = new ArrayList<>();

    public UserRVAdapter(List<User> users, Context context) {
        UserRVAdapter.users = users;
        this.context = context;
    }

    public void setUsers(List<User> users) {
        UserRVAdapter.users = users;
    }

    public static List<User> getUsers() {
        return users;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
                return new UserViewHolder(view, context);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_user_card_view, parent, false);
                return new InfoViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.draw(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < users.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return users.size() + 1;
    }
}
