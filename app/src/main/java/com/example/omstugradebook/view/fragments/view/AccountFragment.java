package com.example.omstugradebook.view.fragments.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.recyclerview.adapter.UserRVAdapter;
import com.example.omstugradebook.view.fragments.Updatable;
import com.example.omstugradebook.view.fragments.viewmodel.AccountViewModel;

import java.util.List;

public class AccountFragment extends Fragment implements Updatable {
    private final UserRVAdapter adapter = new UserRVAdapter(this);
    private AccountViewModel accountViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.accountManagement));
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initRecyclerView(view);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getUsersLiveData().observe(getViewLifecycleOwner(), this::update);
        accountViewModel.getErrorLiveData().observe(getViewLifecycleOwner(),
                error -> Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show());
        accountViewModel.getUsers();
        accountViewModel.checkAuthActiveUser();
        return view;
    }

    @Override
    public void update() {
        accountViewModel.getUsers();
    }

    private void update(List<User> users) {
        adapter.setUsers(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.user_recycle_view);
        recyclerView.setAdapter(adapter);
    }

}
