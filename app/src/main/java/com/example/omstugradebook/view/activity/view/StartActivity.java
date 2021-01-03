package com.example.omstugradebook.view.activity.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.view.activity.viewmodel.StartViewModel;

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        StartViewModel startViewModel = new ViewModelProvider(this).get(StartViewModel.class);

        startViewModel.getActiveUserLiveData().observe(this, value -> {
            if (!value) {
                startLoginActivity();
            } else {
                startMainActivity();
            }
        });

        startViewModel.checkAuthActiveUser();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getType() != null) {
            switch (data.getType()) {
                case "MAIN":
                    this.finish();
                    break;
                case "LOGIN":
                    startMainActivity();
                    break;
            }
        }
    }
}