package com.example.omstugradebook.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.R;
import com.example.omstugradebook.adapter.UserRVAdapter;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.model.User;

public class AccountFragment extends Fragment implements View.OnClickListener {
    OmSTUSender omSTUSender = new OmSTUSender();
    private UserTable userTable;
    private User activeUser;
    private static final String TAG = "User Fragment Logs";
    private UserRVAdapter adapter;
    private RecyclerView recyclerView;

    public AccountFragment() {

    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        userTable = new UserTable(getContext());
        activeUser = userTable.getActiveUser();
        recyclerView = view.findViewById(R.id.user_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserRVAdapter(userTable.readAllUsers(), getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void onClick(View v) {
        if (userTable.getActiveUser() == null) {
            return;
        }
        try {
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
        }
    }

}
