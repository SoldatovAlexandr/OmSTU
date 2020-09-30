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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.SubjectDaoImpl;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.Term;
import com.example.omstugradebook.recyclerview.adapter.GradeRVAdapter;
import com.example.omstugradebook.recyclerview.holder.subject.SubjectContentHolderConverter;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GradeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Updatable {
    private final GradeRVAdapter adapter = new GradeRVAdapter();
    private final SubjectDao subjectDao = new SubjectDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private static int activeTerm = 1;
    private static int countTerms = 0;
    private static final String TAG = "Grade Fragment Logs";

    private RecyclerView recyclerView;
    private TextView information;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.setRefreshing(false);
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
                List<Subject> subjects = subjectDao.readSubjectsByTerm(i, getContext());
                adapter.setSubjects(new SubjectContentHolderConverter(subjects));
                adapter.notifyDataSetChanged();
                requireActivity().setTitle("Семестр " + i);
                Log.d(TAG, "Выбран семестр номер " + i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update() {
        onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Зачетная книжка");
        countTerms = subjectDao.getCountTerm(getContext());
        setHasOptionsMenu(true);
        List<Subject> subjectList = subjectDao.readSubjectsByTerm(activeTerm, getContext());
        if (!subjectList.isEmpty()) {
            requireActivity().setTitle("Семестр " + activeTerm);
        }
        adapter.setSubjects(new SubjectContentHolderConverter(subjectList));
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        information = view.findViewById(R.id.grade_information);
        if (userDao.getActiveUser(getContext()) == null) {
            information.setText("Чтобы пользоваться зачетной книжкой, войдите в аккаунт.");
        } else {
            information.setText("");
        }
        return view;
    }

    @Override
    public void onRefresh() {
        if (userDao.getActiveUser(getContext()) != null) {
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
            AuthService auth = new AuthService();
            gradeBook = auth.getGradeBook(getContext());
            if (gradeBook == null) {
                return null;
            }
            List<Subject> subjects = new ArrayList<>();
            for (Term term : gradeBook.getTerms()) {
                subjects.addAll(term.getSubjects());
            }
            if (!subjects.equals(subjectDao.readAllSubjects(getContext()))) {
                subjectDao.removeAllSubjects(getContext());
                subjectDao.insertAllSubjects(subjects, getContext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (MainActivity.getNavigation().getSelectedItemId() == R.id.bottom_navigation_item_grade) {
                List<Subject> subjects = subjectDao.readSubjectsByTerm(activeTerm, getContext());
                adapter.setSubjects(new SubjectContentHolderConverter(subjects));
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
                if (gradeBook == null) {
                    Toast.makeText(getContext(), "Проблемы с подключением к серверу", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

