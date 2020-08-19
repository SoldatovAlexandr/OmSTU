package com.example.omstugradebook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.omstugradebook.R;

public class TimetableFragment extends Fragment {
    private static TimetableFragment instance;

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
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }
}
