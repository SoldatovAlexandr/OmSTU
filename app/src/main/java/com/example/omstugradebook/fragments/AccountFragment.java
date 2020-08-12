package com.example.omstugradebook.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.model.User;

public class AccountFragment extends Fragment implements View.OnClickListener {
    OmSTUSender omSTUSender = new OmSTUSender();
    private UserTable userTable;
    private User activeUser;
    private static final String TAG = "User Fragment Logs";
    TextView textView;
    Button button;

    public AccountFragment() {

    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userTable = new UserTable(getContext());
        activeUser = userTable.getActiveUser();
        if (activeUser == null) {
            User user = new User("Aleksandr_Soldatov", "cjcbcjxrf123", "");
            userTable.insert(user);
            activeUser = user;
        }
        Log.d(TAG, activeUser + " is active user");
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        textView = view.findViewById(R.id.cookie);
        button = view.findViewById(R.id.button_ok);
        button.setOnClickListener(this);
        button.setText("Get token");
        return view;
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        try {
            Log.d(TAG, "нажата кнопка");
            omSTUSender.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private String cookie = "";
        private String studSesId = "";

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "сделан запрос в doInBackground");
            Auth auth = new Auth();
            cookie = auth.getAuth(activeUser.getLogin(), activeUser.getPassword());
            if (cookie.equals("error")) {
                return null;
            }
            studSesId = auth.getStudSessId(cookie);
            activeUser.setToken(studSesId);
            userTable.update(activeUser);
            Log.d(TAG, "studSessId equals " + studSesId);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute");
            textView.setText("Вы вошли на сайт!");
        }
    }
}
