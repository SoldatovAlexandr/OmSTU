package com.example.omstugradebook.presentation.view.fragments.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.data.model.grade.Subject;
import com.example.omstugradebook.presentation.recyclerview.adapter.GradeRVAdapter;
import com.example.omstugradebook.presentation.recyclerview.holder.subject.SubjectContentHolderConverter;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.GradeViewModel;

import java.util.List;

public class GradeFragment extends Fragment{
    private final GradeRVAdapter adapter = new GradeRVAdapter();

    private GradeViewModel gradeViewModel;

    private static int activeTerm = 1;

    private int countTerms = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        requireActivity().setTitle("Зачетная книжка");

        RecyclerView recyclerView = view.findViewById(R.id.rv);

        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);

        gradeViewModel = new ViewModelProvider(this).get(GradeViewModel.class);

        gradeViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::showToastMessage);

        gradeViewModel.getSubjectsLiveData().observe(getViewLifecycleOwner(), gradeModel -> {
            countTerms = gradeModel.getCountTerms();

            update(gradeModel.getSubjects());
        });

        gradeViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), this::setTitle);

        gradeViewModel.getSubjects(activeTerm);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        for (int i = 1; i <= 10; i++) {
            menu.add("Семестр " + i);
        }
        inflater.inflate(R.menu.term_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        for (int i = 1; i <= countTerms; i++) {
            if (title.equals("Семестр " + i)) {
                activeTerm = i;

                gradeViewModel.getSubjects(i);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void update(List<Subject> subjects) {
        adapter.setSubjects(new SubjectContentHolderConverter(subjects));

        adapter.notifyDataSetChanged();
    }

    private void setTitle(String title) {
        requireActivity().setTitle(title);
    }
}

