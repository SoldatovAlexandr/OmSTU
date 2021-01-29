package com.example.omstugradebook.presentation.view.fragments.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.contactwork.ContactWork;
import com.example.omstugradebook.presentation.recyclerview.adapter.ContactWorkRVAdapter;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.ContactWorkViewModel;

import java.util.List;

public class ContactWorkFragment extends Fragment {

    private final ContactWorkRVAdapter adapter = new ContactWorkRVAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_work, container, false);

        requireActivity().setTitle(getString(R.string.contactWork));

        initRecyclerView(view);

        //TODO
        ContactWorkViewModel contactWorkViewModel = new ViewModelProvider(this).get(ContactWorkViewModel.class);

        contactWorkViewModel.getContactWorkLiveData().observe(getViewLifecycleOwner(),
                contactWorkModel -> update(contactWorkModel.getContactWorks()));

        contactWorkViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::printError);

        contactWorkViewModel.getContactWorks();
        return view;
    }

    private void update(List<ContactWork> contactWorks) {
        adapter.setContactWorks(contactWorks);

        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.contact_work_recycler_view);

        recyclerView.setAdapter(adapter);
    }

    private void printError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
