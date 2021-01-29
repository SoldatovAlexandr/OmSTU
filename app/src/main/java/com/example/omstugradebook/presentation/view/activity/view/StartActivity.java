package com.example.omstugradebook.presentation.view.activity.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.presentation.view.activity.viewmodel.StartViewModel;

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private static final String MAIN = "MAIN";

    private static final String LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        StartViewModel startViewModel = new ViewModelProvider(this).get(StartViewModel.class);

        startViewModel.getStartLoginLiveData().observe(this, value -> startLoginActivity());

        startViewModel.getStartMainLiveData().observe(this, value -> startMainActivity());

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
                case MAIN:
                    this.finish();
                    break;
                case LOGIN:
                    startMainActivity();
                    break;
            }
        }
    }
}