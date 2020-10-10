package com.example.omstugradebook.view.activity.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.recyclerview.adapter.ContactWorkListRVAdapter;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.view.activity.viewmodel.ContactWorkTasksViewModel;

public class ContactWorkTasksActivity extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private ContactWorksTask task;

    private final View.OnClickListener listener = v -> {
        task = getOnClickTask(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                //permission denied, request it
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            } else {
                //permission  already granted, perform download
                startDownloading();
            }
        } else {
            //system os is less than marshmallow, perform download
            startDownloading();
        }
        task = null;
    };
    private final ContactWorkListRVAdapter adapter = new ContactWorkListRVAdapter(listener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_work_list);
        initFields();

        Bundle arguments = getIntent().getExtras();
        setTitle(arguments);

        ContactWorkTasksViewModel cwViewModel = new ViewModelProvider(this)
                .get(ContactWorkTasksViewModel.class);
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

    private ContactWorksTask getOnClickTask(View v) {
        TextView textView = (TextView) v;
        for (ContactWorksTask task : adapter.getContactWorksTasksList()) {
            if (textView.getText().toString().equals(task.getFile())) {
                return task;
            }
        }
        return null;
    }

    private void startDownloading() {
        String fileName = task.getFile().trim().replaceAll(" ", "_");
        if (task != null) {
            String message = new ContactWorkService().downloadFile(task.getLink(), fileName, this);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading();
            } else {
                Toast.makeText(this, "Система отказывает в доступе!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}