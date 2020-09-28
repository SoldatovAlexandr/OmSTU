package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.Student;
import com.example.omstugradebook.model.grade.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
    }

    @Override
    public long insert(User user, Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("login", user.getLogin());
            contentValues.put("password", user.getPassword());
            contentValues.put("token", user.getToken());
            Student student = user.getStudent();
            contentValues.put("fullName", student.getFullName());
            contentValues.put("numberGradeBook", student.getNumberGradeBook());
            contentValues.put("speciality", student.getSpeciality());
            contentValues.put("educationForm", student.getEducationForm());
            contentValues.put("isActive", user.getIsActive());
            long id = database.insert("users", null, contentValues);
            user.setId(id);
            return id;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public User getUserByLogin(String login, Context context) {
        return getUser("login = '" + login + "'", context);
    }

    @Override
    public User getUserByToken(String token, Context context) {
        return getUser("token = '" + token + "'", context);
    }

    @Override
    public User getUserById(long id, Context context) {
        return getUser("id = " + id, context);
    }


    @Override
    public User getActiveUser(Context context) {
        for (User user : readAllUsers(context)) {
            if (user.getIsActive() == 1) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int removeAllUsers(Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete("users", null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long update(User user, Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("login", user.getLogin());
            contentValues.put("password", user.getPassword());
            contentValues.put("token", user.getToken());
            Student student = user.getStudent();
            contentValues.put("fullName", student.getFullName());
            contentValues.put("numberGradeBook", student.getNumberGradeBook());
            contentValues.put("speciality", student.getSpeciality());
            contentValues.put("educationForm", student.getEducationForm());
            contentValues.put("isActive", user.getIsActive());
            long id = database.update("users",
                    contentValues,
                    "id =" + user.getId(),
                    null);
            user.setId(id);
            return id;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<User> readAllUsers(Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query("users",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            return getUsers(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void changeActiveUser(User newUser, Context context) {
        User user = getActiveUser(context);
        if (user != null) {
            user.setIsActive(0);
            update(user, context);
        }
        newUser.setIsActive(1);
        update(newUser, context);
    }

    @Override
    public boolean removeUser(User user, Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            if (user.getIsActive() == 1) {
                List<User> users = readAllUsers(context);
                if (users != null) {
                    changeActiveUser(users.get(0), context);
                }
            }
            int result = database.delete("users", "id =" + user.getId(), null);
            return result > 0;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User getUser(String selection, Context context) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(context); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return getUser(database.query("users",
                    null,
                    selection,
                    null,
                    null,
                    null,
                    null));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }


    private User getUser(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int loginColIndex = cursor.getColumnIndex("login");
            int passwordColIndex = cursor.getColumnIndex("password");
            int tokenColIndex = cursor.getColumnIndex("token");
            int fullNameColIndex = cursor.getColumnIndex("fullName");
            int numberGBColIndex = cursor.getColumnIndex("numberGradeBook");
            int specialityColIndex = cursor.getColumnIndex("speciality");
            int educationFormColIndex = cursor.getColumnIndex("educationForm");
            int isActiveColIndex = cursor.getColumnIndex("isActive");
            long id = cursor.getLong(idColIndex);
            long isActive = cursor.getLong(isActiveColIndex);
            String login = cursor.getString(loginColIndex);
            String password = cursor.getString(passwordColIndex);
            String token = cursor.getString(tokenColIndex);
            String fullName = cursor.getString(fullNameColIndex);
            String numberGradeBook = cursor.getString(numberGBColIndex);
            String speciality = cursor.getString(specialityColIndex);
            String educationForm = cursor.getString(educationFormColIndex);
            Student student = new Student(fullName, numberGradeBook, speciality, educationForm);
            return new User(id, login, password, token, student, isActive);
        }
        return null;
    }

    private List<User> getUsers(Cursor cursor) {
        List<User> users = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int loginColIndex = cursor.getColumnIndex("login");
            int passwordColIndex = cursor.getColumnIndex("password");
            int tokenColIndex = cursor.getColumnIndex("token");
            int fullNameColIndex = cursor.getColumnIndex("fullName");
            int numberGBColIndex = cursor.getColumnIndex("numberGradeBook");
            int specialityColIndex = cursor.getColumnIndex("speciality");
            int educationFormColIndex = cursor.getColumnIndex("educationForm");
            int isActiveColIndex = cursor.getColumnIndex("isActive");
            do {
                String login = cursor.getString(loginColIndex);
                String password = cursor.getString(passwordColIndex);
                String token = cursor.getString(tokenColIndex);
                int id = cursor.getInt(idColIndex);
                long isActive = cursor.getLong(isActiveColIndex);
                String fullName = cursor.getString(fullNameColIndex);
                String numberGradeBook = cursor.getString(numberGBColIndex);
                String speciality = cursor.getString(specialityColIndex);
                String educationForm = cursor.getString(educationFormColIndex);
                Student student = new Student(fullName, numberGradeBook, speciality, educationForm);
                users.add(new User(id, login, password, token, student, isActive));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

}
