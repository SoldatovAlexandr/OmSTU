package com.example.omstugradebook.view.fragments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.omstugradebook.R;
import com.example.omstugradebook.model.grade.Student;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.view.activity.view.LoginActivity;
import com.example.omstugradebook.view.fragments.viewmodel.AccountViewModel;

public class AccountFragment extends Fragment {
    private AccountViewModel accountViewModel;
    private TextView fullName;
    private TextView numberGradeBook;
    private TextView speciality;
    private TextView educationForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.accountManagement));

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        accountViewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::updateUser);
        accountViewModel.getErrorLiveData().observe(getViewLifecycleOwner(),
                error -> Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show());
        accountViewModel.getUser();

        return view;
    }

    private void updateUser(User user) {
        if (user != null) {
            getActivity().setTitle(user.getLogin());
            Student student = user.getStudent();
            fullName.setText(student.getFullName());
            numberGradeBook.setText(student.getNumberGradeBook());
            speciality.setText(student.getSpeciality());
            educationForm.setText(student.getEducationForm());
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    private void initViews(View view) {
        fullName = view.findViewById(R.id.fullName);
        numberGradeBook = view.findViewById(R.id.numberGradeBook);
        speciality = view.findViewById(R.id.speciality);
        educationForm = view.findViewById(R.id.educationForm);
        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            accountViewModel.logoutUser();
            startLoginActivity();
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        accountViewModel.getUser();
    }

}
