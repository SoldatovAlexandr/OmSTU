package com.example.omstugradebook.view.fragments;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.view.activity.MainActivity;
import com.example.omstugradebook.recyclerview.adapter.GradeRVAdapter;
import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.SubjectDaoImpl;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.GradeBook;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.model.Term;

import java.util.ArrayList;
import java.util.List;

public class GradeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private GradeRVAdapter adapter = new GradeRVAdapter(new ArrayList<Subject>());
    private SubjectDao subjectDao;
    private UserDao userDao;
    private static int activeTerm = 1;
    private static final String TAG = "Grade Fragment Logs";
    private static int countTerms = 0;
    private static GradeFragment instance;

    private SwipeRefreshLayout swipeRefreshLayout;

    private GradeFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.setRefreshing(false);
    }

    public static GradeFragment getInstance() {
        if (instance == null) {
            instance = new GradeFragment();
        }
        return instance;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
                adapter.setSubjects(subjectDao.readSubjectsByTerm(i));
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Выбран семестр номер " + i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void update() {
        onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subjectDao = new SubjectDaoImpl(getContext());
        userDao = new UserDaoImpl(getContext());
        countTerms = subjectDao.getCountTerm();
        setHasOptionsMenu(true);
        List<Subject> subjectList = subjectDao.readSubjectsByTerm(activeTerm);
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
        userDao = new UserDaoImpl(getContext());
        if (userDao.getActiveUser() != null) {
            OmSTUSender omSTUSender = new OmSTUSender();
            omSTUSender.execute();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private GradeBook gradeBook;

        @Override
        protected String doInBackground(String... strings) {
            Auth auth = new Auth();
            gradeBook = auth.getGradeBook(getContext());
            if (gradeBook == null) {
                return null;
            }
            List<Subject> subjects = new ArrayList<>();
            for (Term term : gradeBook.getTerms()) {
                subjects.addAll(term.getSubjects());
            }
            if (!subjects.equals(subjectDao.readAllSubjects())) {
                subjectDao.removeAllSubjects();
                subjectDao.insertAllSubjects(subjects);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (MainActivity.getNavigation().getSelectedItemId() == R.id.bottom_navigation_item_grade) {
                adapter.setSubjects(subjectDao.readSubjectsByTerm(activeTerm));
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
                if (gradeBook == null) {
                    Toast.makeText(getContext(), "Проблемы с подключением к серверу", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

