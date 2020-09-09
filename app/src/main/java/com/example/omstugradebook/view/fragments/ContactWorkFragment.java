package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ContactWorkFragment extends Fragment {
    private static String PATH = "http://up.omgtu.ru/index.php?r=remote/read";
    static final String STUD_SES_ID = "STUDSESSID";
    private UserDao userDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private Document doc;

        @Override
        protected String doInBackground(String... strings) {
            userDao = new UserDaoImpl(getContext());
            String token = userDao.getActiveUser().getToken();
            try {
                doc = Jsoup.connect(PATH).cookie(STUD_SES_ID, token).get();
                System.out.println(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
