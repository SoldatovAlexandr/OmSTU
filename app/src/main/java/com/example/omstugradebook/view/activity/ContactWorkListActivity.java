package com.example.omstugradebook.view.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.recyclerview.adapter.ContactWorkListRVAdapter;
import com.example.omstugradebook.service.ContactWorkService;

import java.util.List;
import java.util.Objects;

public class ContactWorkListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactWorkListRVAdapter adapter = new ContactWorkListRVAdapter();
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        //to check validations path
        path = Objects.requireNonNull(arguments.get("path")).toString();
        //set title
        String discipline = Objects.requireNonNull(arguments.get("discipline")).toString();
        setTitle(discipline);
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
        setContentView(R.layout.activity_contact_work_list);
        recyclerView = findViewById(R.id.contact_work_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private Context getContext() {
        return this;
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private List<ContactWorksTask> contactWorksTasks;

        @Override
        protected String doInBackground(String... strings) {
            ContactWorkService contactWorkService = new ContactWorkService();
            contactWorksTasks = contactWorkService.getTaskContactWork(path, getContext());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (contactWorksTasks != null) {
                adapter.setContactWorksTasksList(contactWorksTasks);
                adapter.notifyDataSetChanged();
            }
        }
    }
}