package com.example.omstugradebook.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.recyclerview.adapter.ContactWorkRVAdapter;
import com.example.omstugradebook.service.ContactWorkService;

import java.util.ArrayList;
import java.util.List;

public class ContactWorkFragment extends Fragment implements Updatable {
    private RecyclerView recyclerView;
    private ContactWorkRVAdapter adapter = new ContactWorkRVAdapter(new ArrayList<ContactWork>(), getContext());
    private UserDao userDao = new UserDaoImpl();
    private TextView information;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OmSTUSender omSTUSender = new OmSTUSender();
        omSTUSender.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().setTitle("Контактная работа");
        View view = inflater.inflate(R.layout.fragment_contact_work, container, false);
        recyclerView = view.findViewById(R.id.contact_work_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        information = view.findViewById(R.id.contact_work_information);
        if (userDao.getActiveUser(getContext()) == null) {
            information.setText("Чтобы пользоваться контактной работой, войдите в аккаунт.");
        } else {
            information.setText("");
        }
        return view;
    }


    @Override
    public void update() {

    }

    class OmSTUSender extends AsyncTask<String, String, String> {
        private List<ContactWork> contactWorks;

        @Override
        protected String doInBackground(String... strings) {
            ContactWorkService contactWorkService = new ContactWorkService();
            contactWorks = contactWorkService.getContactWork(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (contactWorks == null) {
                return;
            }
            information.setText("");
            adapter.setContactWorks(contactWorks, getContext());
            adapter.notifyDataSetChanged();
        }
    }
}
