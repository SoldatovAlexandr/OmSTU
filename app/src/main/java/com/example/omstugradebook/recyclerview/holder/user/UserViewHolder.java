package com.example.omstugradebook.recyclerview.holder.user;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.Student;
import com.example.omstugradebook.model.User;
import com.example.omstugradebook.recyclerview.adapter.UserRVAdapter;
import com.example.omstugradebook.view.fragments.AccountFragment;

public class UserViewHolder extends AccountViewHolder implements View.OnClickListener {

    private CardView cardView;
    private TextView fullName;
    private TextView numberGradeBook;
    private TextView speciality;
    private TextView educationForm;
    private TextView login;
    private ImageButton deleteButton;
    private ImageButton isActiveButton;
    private UserDao userDao;

    public UserViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        userDao = new UserDaoImpl(context);
        cardView = itemView.findViewById(R.id.user_card_view);
        fullName = itemView.findViewById(R.id.fullName);
        numberGradeBook = itemView.findViewById(R.id.numberGradeBook);
        speciality = itemView.findViewById(R.id.speciality);
        educationForm = itemView.findViewById(R.id.educationForm);
        login = itemView.findViewById(R.id.login_user_card_view);
        deleteButton = itemView.findViewById(R.id.delete_user_button);
        deleteButton.setOnClickListener(this);
        UserRVAdapter.buttons.add(deleteButton);
        isActiveButton = itemView.findViewById(R.id.is_active_user_button);
        //установить слушателя на пользователя, так же сделать удаление элемента из view про свайпе в лево
        // cardView.setOnClickListener();
    }

    @Override
    public void draw(int position) {
        User user = UserRVAdapter.getUsers().get(position);
        Student student = user.getStudent();
        if (user.getIsActive() == 1) {
            this.isActiveButton.setColorFilter(Color.GREEN);
        } else {
            this.isActiveButton.setColorFilter(Color.GRAY);
        }
        this.fullName.setText(student.getFullName());
        this.speciality.setText(student.getSpeciality());
        this.numberGradeBook.setText(student.getNumberGradeBook());
        this.educationForm.setText(student.getEducationForm());
        this.login.setText(user.getLogin());
        this.deleteButton.setId(position);
    }

    @Override
    public void onClick(View v) {
        for (ImageButton button : UserRVAdapter.buttons) {
            if (v.getId() == button.getId()) {
                User user = userDao.getUserByLogin(login.getText().toString());
                if (userDao.removeUser(user)) {
                    //AccountFragment.getInstance().update();
                }
            }
        }
    }
}