package com.example.omstugradebook.view.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.view.activity.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private Button buttonOk;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFields();
        setTitle(getString(R.string.signIn));

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getUserLiveData().observe(this, user -> {
            showToastMessage(user.getLogin() + " авторизован!");
            returnResultAndFinish();
        });

        loginViewModel.getErrorLiveData().observe(this, error -> {
            showToastMessage(getString(error));
            buttonOk.setEnabled(true);
        });

        loginViewModel.getInfoLiveData().observe(this, info -> showToastMessage(getString(info)));

        buttonOk.setOnClickListener(v -> {
            buttonOk.setEnabled(false);
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            validationFieldsForSignIn(login, password);
            loginViewModel.signIn(login, password);
        });
    }


    @Override
    public void onBackPressed() {
        showToastMessage(R.string.youAreNotLogin);
    }

    private void initFields() {
        loginEditText = findViewById(R.id.login_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        buttonOk = findViewById(R.id.login_button_ok);
    }

    private void validationFieldsForSignIn(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            showToastMessage(R.string.emptyFields);
            buttonOk.setEnabled(true);
        }
    }

    private void returnResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.login), loginEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showToastMessage(int id) {
        showToastMessage(getString(id));
    }
}