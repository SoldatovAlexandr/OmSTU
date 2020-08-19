package com.example.omstugradebook.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omstugradebook.R;
import com.example.omstugradebook.database.UserTable;
import com.example.omstugradebook.fragments.AccountFragment;
import com.example.omstugradebook.model.Student;
import com.example.omstugradebook.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UserViewHolder> {
    private List<User> users;
    private UserTable userTable;
    private List<Button> buttons = new ArrayList<>();
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
    public UserRVAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        Student student = user.getStudent();
        if (user.getIsActive() == 1) {
            holder.isActive.setText("Активный");
        } else {
            holder.isActive.setText("");
        }
        holder.fullName.setText(student.getFullName());
        holder.speciality.setText(student.getSpeciality());
        holder.numberGradeBook.setText(student.getNumberGradeBook());
        holder.educationForm.setText(student.getEducationForm());
        holder.login.setText(user.getLogin());
        holder.button.setId(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private TextView isActive;
        private TextView fullName;
        private TextView numberGradeBook;
        private TextView speciality;
        private TextView educationForm;
        private TextView login;
        private Button button;
        private final String USER_REMOVED = "Пользователь удален";

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            isActive = itemView.findViewById(R.id.is_active_user_card_view);
            cardView = itemView.findViewById(R.id.user_card_view);
            fullName = itemView.findViewById(R.id.fullName);
            numberGradeBook = itemView.findViewById(R.id.numberGradeBook);
            speciality = itemView.findViewById(R.id.speciality);
            educationForm = itemView.findViewById(R.id.educationForm);
            login = itemView.findViewById(R.id.login_user_card_view);
            button = itemView.findViewById(R.id.delete_user_button);
            button.setOnClickListener(this);
            buttons.add(button);
            //установить слушателя на пользователя, так же сделать удаление элемента из view про свайпе в лево
            // cardView.setOnClickListener();
        }

        @Override
        public void onClick(View v) {
            for (Button button : buttons) {
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
