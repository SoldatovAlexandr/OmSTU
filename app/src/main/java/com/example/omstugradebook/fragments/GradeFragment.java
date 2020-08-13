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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.Auth;
import com.example.omstugradebook.ConnectionDetector;
import com.example.omstugradebook.R;
import com.example.omstugradebook.RVAdapter;
import com.example.omstugradebook.database.SubjectTable;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.model.GradeBook;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.model.Term;
import com.example.omstugradebook.model.User;

import java.util.ArrayList;
import java.util.List;

public class GradeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RVAdapter adapter = new RVAdapter(new ArrayList<Subject>());
    private SubjectTable subjectTable;
    private static int activeTerm = 1;
    private static final String TAG = "Grade Fragment Logs";
    private static int countTerms = 0;

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
        countTerms = subjectTable.getCountTerm();
        setHasOptionsMenu(true);
        List<Subject> subjectList = subjectTable.readSubjectsByTerm(activeTerm);
        adapter.setSubjects(subjectList);
        ConnectionDetector connectionDetector = new ConnectionDetector(getContext());
        if (connectionDetector.ConnectingToInternet()) {
            Log.d(TAG, "есть соединение с интернетом");
            OmSTUSender omSTUSender = new OmSTUSender();
            omSTUSender.execute();
        }
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private GradeBook gradeBook = null;
        UserTable userTable = new UserTable(getContext());


        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "Вызван метод doInBackground");
            Auth auth = new Auth();
            if (userTable.getActiveUser() == null) {
                return null;
            }
            gradeBook = auth.getGradeBook(getContext());
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "Вызван метод onPostExecute");
            if (gradeBook != null) {
                List<Subject> subjects = new ArrayList<>();
                for (Term term : gradeBook.getTerms()) {
                    subjects.addAll(term.getSubjects());
                }
                Log.d(TAG, "из запроса получены все элементы");
                SubjectTable subjectTable = new SubjectTable(getContext());
                User user = userTable.getActiveUser();
                user.setStudent(gradeBook.getStudent());
                userTable.update(user);

                if (!subjectTable.equalsSubjects(subjects)) {
                    subjectTable.readSubjectsByTerm(activeTerm);
                    subjectTable.removeAllSubjects();
                    subjectTable.insertAllSubjects(subjects);
                }
                countTerms = subjectTable.getCountTerm();
                List<Subject> subjectList = new ArrayList<>();
                for (Subject subject : subjects) {
                    if (subject.getTerm() == activeTerm) {
                        subjectList.add(subject);
                    }
                }
                adapter.setSubjects(subjectList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
