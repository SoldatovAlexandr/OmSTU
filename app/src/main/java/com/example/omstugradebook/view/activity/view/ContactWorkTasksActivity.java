package com.example.omstugradebook.view.activity.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.recyclerview.adapter.ContactWorkListRVAdapter;
import com.example.omstugradebook.view.activity.viewmodel.ContactWorkTasksViewModel;

public class ContactWorkTasksActivity extends AppCompatActivity {

    private final ContactWorkListRVAdapter adapter = new ContactWorkListRVAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_work_list);
        initFields();

        Bundle arguments = getIntent().getExtras();
        setTitle(arguments);

        ContactWorkTasksViewModel cwViewModel = new ViewModelProvider(this).get(ContactWorkTasksViewModel.class);
        cwViewModel.getContactWorkModelLiveData().observe(this, contactWorkModel -> {
            adapter.setContactWorksTasksList(contactWorkModel.getContactWorksTasks());
            adapter.notifyDataSetChanged();
        });
        final String path = arguments.get(getString(R.string.path)).toString();
        cwViewModel.sendRequestToGetContactWorkTasks(path);

    }

    private void setTitle(Bundle bundle) {
        if (bundle != null) {
            setTitle((bundle.get(getString(R.string.discipline))).toString());
        }
    }

    private void initFields() {
        RecyclerView recyclerView = findViewById(R.id.contact_work_list_recycler_view);
        recyclerView.setAdapter(adapter);
    }

}