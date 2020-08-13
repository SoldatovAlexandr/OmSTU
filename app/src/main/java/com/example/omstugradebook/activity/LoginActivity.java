package com.example.omstugradebook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.omstugradebook.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonOk;
    private EditText login;
    private EditText password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonOk = findViewById(R.id.login_button_ok);
        buttonOk.setOnClickListener(this);
        login = findViewById(R.id.et_input_login);
        password = findViewById(R.id.et_input_password);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("login", login.getText().toString());
        intent.putExtra("password", password.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}