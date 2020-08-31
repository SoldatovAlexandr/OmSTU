package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.omstugradebook.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TimetableFragment extends Fragment {
    private static TimetableFragment instance;
    private String path = "https://rasp.omgtu.ru/api/schedule/group/351?start=2020.08.31&finish=2020.09.06&lng=1";

    private TimetableFragment() {

    }

    public static TimetableFragment getInstance() {
        if (instance == null) {
            instance = new TimetableFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    class OmSTUSender extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Document doc;
            try {
                doc = Jsoup.connect(path).ignoreContentType(true).get();
                System.out.println(doc.text());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
