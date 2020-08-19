package com.example.omstugradebook.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.adapter.GradeRVAdapter;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.SubjectTable;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.model.GradeBook;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.model.Term;

import java.util.ArrayList;
import java.util.List;

public class GradeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private GradeRVAdapter adapter = new GradeRVAdapter(new ArrayList<Subject>());
    private SubjectTable subjectTable;
    private UserTable userTable;
    private static int activeTerm = 1;
    private static final String TAG = "Grade Fragment Logs";
    private static int countTerms = 0;

    private SwipeRefreshLayout swipeRefreshLayout;

    public GradeFragment() {

    }

    public static GradeFragment newInstance() {
        return new GradeFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (int i = 1; i <= countTerms; i++) {
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
                adapter.setSubjects(subjectTable.readSubjectsByTerm(i));
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Выбран семестр номер " + i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        subjectTable = new SubjectTable(getContext());
        userTable = new UserTable(getContext());
        countTerms = subjectTable.getCountTerm();
        setHasOptionsMenu(true);
        List<Subject> subjectList = subjectTable.readSubjectsByTerm(activeTerm);
        adapter.setSubjects(subjectList);
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onRefresh() {
        userTable = new UserTable(getContext());
        if (userTable.getActiveUser() != null) {
            OmSTUSender omSTUSender = new OmSTUSender();
            omSTUSender.execute();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        GradeBook gradeBook;

        @Override
        protected String doInBackground(String... strings) {
            Auth auth = new Auth();
            gradeBook = auth.getGradeBook(getContext());
            if (gradeBook == null) {
                Toast.makeText(getContext(), "Проблемы с подключением к серверу", Toast.LENGTH_SHORT).show();
                return null;
            }
            List<Subject> subjects = new ArrayList<>();
            for (Term term : gradeBook.getTerms()) {
                subjects.addAll(term.getSubjects());
            }
            if (!subjects.equals(subjectTable.readAllSubjects())) {
                subjectTable.removeAllSubjects();
                subjectTable.insertAllSubjects(subjects);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.setSubjects(subjectTable.readSubjectsByTerm(activeTerm));
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }
}

