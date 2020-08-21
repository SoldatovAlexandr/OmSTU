package com.example.omstugradebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.activity.LoginActivity;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.fragments.AccountFragment;
import com.example.omstugradebook.model.Student;
import com.example.omstugradebook.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.AbstractViewHolder> {
    private List<User> users;
    private UserTable userTable;
    private List<ImageButton> buttons = new ArrayList<>();
    private static final String TAG = "User RVAdapter Logs";

    public UserRVAdapter(List<User> users, Context context) {
        this.users = users;
        userTable = new UserTable(context);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserRVAdapter.AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_user_card_view, parent, false);
                return new InfoViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        return new AbstractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        holder.draw(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < users.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return users.size() + 1;
    }

    public class AbstractViewHolder extends RecyclerView.ViewHolder {

        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void draw(int position) {
        }
    }

    public class InfoViewHolder extends AbstractViewHolder implements View.OnClickListener {
        private CardView cardView;
        private Button button;


        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.add_new_user_card_view);
            button = itemView.findViewById(R.id.add_new_user_button);
            button.setOnClickListener(this);
        }

        @Override
        public void draw(int position) {
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            v.getContext().startActivity(intent);
        }
    }

    public class UserViewHolder extends AbstractViewHolder implements View.OnClickListener {

        private CardView cardView;
        private TextView fullName;
        private TextView numberGradeBook;
        private TextView speciality;
        private TextView educationForm;
        private TextView login;
        private ImageButton deleteButton;
        private ImageButton isActiveButton;
        private final String USER_REMOVED = "Пользователь удален";

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.user_card_view);
            fullName = itemView.findViewById(R.id.fullName);
            numberGradeBook = itemView.findViewById(R.id.numberGradeBook);
            speciality = itemView.findViewById(R.id.speciality);
            educationForm = itemView.findViewById(R.id.educationForm);
            login = itemView.findViewById(R.id.login_user_card_view);
            deleteButton = itemView.findViewById(R.id.delete_user_button);
            deleteButton.setOnClickListener(this);
            buttons.add(deleteButton);
            isActiveButton = itemView.findViewById(R.id.is_active_user_button);
            //установить слушателя на пользователя, так же сделать удаление элемента из view про свайпе в лево
            // cardView.setOnClickListener();
        }

        @Override
        public void draw(int position) {
            User user = users.get(position);
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
            for (ImageButton button : buttons) {
                if (v.getId() == button.getId()) {
                    User user = userTable.getUserByLogin(login.getText().toString());
                    if (userTable.removeUser(user)) {
                        Log.d(TAG, USER_REMOVED);
                        AccountFragment.getInstance().update();
                    }
                }
            }
        }
    }

}
