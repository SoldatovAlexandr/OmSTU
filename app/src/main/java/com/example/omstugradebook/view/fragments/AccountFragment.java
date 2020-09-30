package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.recyclerview.adapter.UserRVAdapter;
import com.example.omstugradebook.service.AuthService;

public class AccountFragment extends Fragment implements Updatable {
    private static final String TAG = "User Fragment Logs";
    private final UserDao userDao = new UserDaoImpl();
    private final UserRVAdapter adapter = new UserRVAdapter(this);
    private RecyclerView recyclerView;
    private User activeUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Управление аккаунтами");
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        activeUser = userDao.getActiveUser(getContext());
        recyclerView = view.findViewById(R.id.user_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void update() {
        adapter.setUsers(userDao.readAllUsers(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }


    class OmSTUSender extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "сделан запрос в doInBackground");
            AuthService auth = new AuthService();
            String cookie = auth.getAuth(activeUser.getLogin(), activeUser.getPassword());
            if (cookie.equals("error")) {
                return null;
            }
            String studSesId = auth.getStudSessId(cookie);
            activeUser.setToken(studSesId);
            userDao.update(activeUser, getContext());
            Log.d(TAG, "studSessId equals " + studSesId);
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute");
            adapter.setUsers(userDao.readAllUsers(getContext()));
            adapter.notifyDataSetChanged();
        }
    }
}
