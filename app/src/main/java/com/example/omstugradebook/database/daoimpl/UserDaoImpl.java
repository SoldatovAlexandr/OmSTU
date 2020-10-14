package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.UserDao;
import com.example.omstugradebook.model.grade.Student;
import com.example.omstugradebook.model.grade.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String USERS = "users";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String FULL_NAME = "fullName";
    private static final String NUMBER_GRADE_BOOK = "numberGradeBook";
    private static final String SPECIALITY = "speciality";
    private static final String EDUCATION_FORM = "educationForm";
    private static final String IS_ACTIVE = "isActive";
    private static final String ID = "id";

    @Override
    public long insert(User user) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(LOGIN, user.getLogin());
            contentValues.put(PASSWORD, user.getPassword());
            contentValues.put(TOKEN, user.getToken());
            Student student = user.getStudent();
            contentValues.put(FULL_NAME, student.getFullName());
            contentValues.put(NUMBER_GRADE_BOOK, student.getNumberGradeBook());
            contentValues.put(SPECIALITY, student.getSpeciality());
            contentValues.put(EDUCATION_FORM, student.getEducationForm());
            contentValues.put(IS_ACTIVE, user.getIsActive());
            long id = database.insert(USERS, null, contentValues);
            user.setId(id);
            return id;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        String selection = "login = ?";
        String[] selectionArgs = new String[]{String.valueOf(login)};
        return getUser(selection, selectionArgs);
    }

    @Override
    public User getUserByToken(String token) {
        String selection = "token = ?";
        String[] selectionArgs = new String[]{String.valueOf(token)};
        return getUser(selection, selectionArgs);
    }

    @Override
    public User getUserById(long id) {
        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return getUser(selection, selectionArgs);
    }


    @Override
    public User getActiveUser() {
        for (User user : readAllUsers()) {
            if (user.getIsActive() == 1) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int removeAllUsers() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete(USERS, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long update(User user) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(LOGIN, user.getLogin());
            contentValues.put(PASSWORD, user.getPassword());
            contentValues.put(TOKEN, user.getToken());
            Student student = user.getStudent();
            contentValues.put(FULL_NAME, student.getFullName());
            contentValues.put(NUMBER_GRADE_BOOK, student.getNumberGradeBook());
            contentValues.put(SPECIALITY, student.getSpeciality());
            contentValues.put(EDUCATION_FORM, student.getEducationForm());
            contentValues.put(IS_ACTIVE, user.getIsActive());
            long id = database.update(USERS,
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
    public List<User> readAllUsers() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(USERS,
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
    public void changeActiveUser(User newUser) {
        User user = getActiveUser();
        if (user != null) {
            user.setIsActive(0);
            update(user);
        }
        newUser.setIsActive(1);
        update(newUser);
    }

    @Override
    public boolean removeUser(User user) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            if (user.getIsActive() == 1) {
                List<User> users = readAllUsers();
                if (users != null) {
                    changeActiveUser(users.get(0));
                }
            }
            String whereClause = "id = ? ";
            String[] whereArgs = new String[]{String.valueOf(user.getId())};
            int result = database.delete(USERS, whereClause, whereArgs);
            return result > 0;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long getUserActiveId() {
        User user = getActiveUser();
        return user != null ? user.getId() : 0;
    }

    private User getUser(String selection, String[] selectionArgs) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return getUser(database.query(USERS,
                    null,
                    selection,
                    selectionArgs,
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
            int idColIndex = cursor.getColumnIndex(ID);
            int loginColIndex = cursor.getColumnIndex(LOGIN);
            int passwordColIndex = cursor.getColumnIndex(PASSWORD);
            int tokenColIndex = cursor.getColumnIndex(TOKEN);
            int fullNameColIndex = cursor.getColumnIndex(FULL_NAME);
            int numberGBColIndex = cursor.getColumnIndex(NUMBER_GRADE_BOOK);
            int specialityColIndex = cursor.getColumnIndex(SPECIALITY);
            int educationFormColIndex = cursor.getColumnIndex(EDUCATION_FORM);
            int isActiveColIndex = cursor.getColumnIndex(IS_ACTIVE);
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
            int idColIndex = cursor.getColumnIndex(ID);
            int loginColIndex = cursor.getColumnIndex(LOGIN);
            int passwordColIndex = cursor.getColumnIndex(PASSWORD);
            int tokenColIndex = cursor.getColumnIndex(TOKEN);
            int fullNameColIndex = cursor.getColumnIndex(FULL_NAME);
            int numberGBColIndex = cursor.getColumnIndex(NUMBER_GRADE_BOOK);
            int specialityColIndex = cursor.getColumnIndex(SPECIALITY);
            int educationFormColIndex = cursor.getColumnIndex(EDUCATION_FORM);
            int isActiveColIndex = cursor.getColumnIndex(IS_ACTIVE);
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
