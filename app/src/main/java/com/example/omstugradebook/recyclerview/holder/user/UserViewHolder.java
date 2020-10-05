package com.example.omstugradebook.recyclerview.holder.user;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.Student;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.view.fragments.Updatable;

public class UserViewHolder extends AccountViewHolder {
    private final TextView fullName;
    private final TextView numberGradeBook;
    private final TextView speciality;
    private final TextView educationForm;
    private final TextView login;
    private final ImageButton deleteButton;
    private final ImageButton isActiveButton;

    public UserViewHolder(@NonNull View itemView, final Updatable updatable) {
        super(itemView);
        CardView cardView = itemView.findViewById(R.id.user_card_view);
        fullName = itemView.findViewById(R.id.fullName);
        numberGradeBook = itemView.findViewById(R.id.numberGradeBook);
        speciality = itemView.findViewById(R.id.speciality);
        educationForm = itemView.findViewById(R.id.educationForm);
        login = itemView.findViewById(R.id.login_user_card_view);
        deleteButton = itemView.findViewById(R.id.delete_user_button);
        isActiveButton = itemView.findViewById(R.id.is_active_user_button);
        cardView.setOnClickListener((v) -> {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserByLogin(login.getText().toString());
            if (user != null) {
                userDao.changeActiveUser(user);
                updatable.update();
            }
        });
        deleteButton.setOnClickListener((v) -> {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserByLogin(login.getText().toString());
            if (user != null) {
                userDao.removeUser(user);
                updatable.update();
            }
        });
    }


    @Override
    public void bind(int position, User user) {
        Student student = user.getStudent();
        this.isActiveButton.setColorFilter(user.getIsActive() == 1 ? Color.GREEN : Color.GRAY);
        this.fullName.setText(student.getFullName());
        this.speciality.setText(student.getSpeciality());
        this.numberGradeBook.setText(student.getNumberGradeBook());
        this.educationForm.setText(student.getEducationForm());
        this.login.setText(user.getLogin());
        this.deleteButton.setId(position);
    }

}