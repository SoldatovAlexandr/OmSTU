package com.example.omstugradebook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.service.AuthService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonOk;
    private EditText login;
    private EditText password;
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Вход");
        buttonOk = findViewById(R.id.login_button_ok);
        buttonOk.setOnClickListener(this);
        login = findViewById(R.id.login_edit_text);
        password = findViewById(R.id.password_edit_text);
        buttonOk.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        buttonOk.setEnabled(false);
        String loginString = login.getText().toString();
        String passwordString = password.getText().toString();
        if (loginString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Не оставляйте поля пустыми", Toast.LENGTH_SHORT).show();
            buttonOk.setEnabled(true);
            return;
        }
        for (User user : userDao.readAllUsers(this)) {
            if (user.getLogin().equals(loginString)) {
                Toast.makeText(this, "Пользователь " + loginString + " уже авторизован", Toast.LENGTH_SHORT).show();
                buttonOk.setEnabled(true);
                return;
            }
        }
        new LoginSender().execute();
    }

    private Context getContext() {
        return this;
    }

    class LoginSender extends AsyncTask<String, String, String> {
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
                Toast.makeText(getApplicationContext(), "Вы успешно авторизовались!\n Подождите пару секунд, почти все готово!", Toast.LENGTH_LONG).show();
                User user = new User(loginString, passwordString, studSesId);
                userDao.insert(user, getContext());
                userDao.changeActiveUser(user, getContext());
                UserDataRequestSender userDataRequestSender = new UserDataRequestSender();
                userDataRequestSender.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Указан неверный логин или пароль", Toast.LENGTH_SHORT).show();
                buttonOk.setEnabled(true);
            }
        }

        class UserDataRequestSender extends AsyncTask<String, String, String> {
            private GradeBook gradeBook;


            @Override
            protected String doInBackground(String... strings) {
                AuthService auth = new AuthService();
                gradeBook = auth.getGradeBook(getContext());
                if (gradeBook != null) {
                    User user = userDao.getActiveUser(getContext());
                    user.setStudent(gradeBook.getStudent());
                    userDao.update(user, getContext());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent();
                intent.putExtra("login", login.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                buttonOk.setEnabled(true);
            }

        }

    }

}