package com.example.omstugradebook.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonOk;
    private EditText login;
    private EditText password;
    private UserDao userDao = new UserDaoImpl(this);

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
        String loginString = login.getText().toString();
        String passwordString = password.getText().toString();
        if (loginString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Не оставляйте поля пустыми", Toast.LENGTH_SHORT).show();
            return;
        }
        for (User user : userDao.readAllUsers()) {
            if (user.getLogin().equals(loginString)) {
                Toast.makeText(this, "Пользователь " + loginString + " уже авторизован", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private String studSesId;

        @Override
        protected String doInBackground(String... strings) {
            AuthService auth = new AuthService();
            String cookie = auth.getAuth(login.getText().toString(), password.getText().toString());
            studSesId = auth.getStudSessId(cookie);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (studSesId != null) {
                String loginString = login.getText().toString();
                String passwordString = password.getText().toString();
                Toast.makeText(getApplicationContext(), "Вы успешно авторизовались!", Toast.LENGTH_SHORT).show();
                User user = new User(loginString, passwordString, studSesId);
                userDao.insert(user);
                userDao.changeActiveUser(user);
                Intent intent = new Intent();
                intent.putExtra("login", login.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Указан неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        }
    }
}