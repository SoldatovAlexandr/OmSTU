package com.example.omstugradebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.model.User;

public class UserTable {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserTable(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public long insert(User user) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", user.getLogin());
        contentValues.put("password", user.getPassword());
        contentValues.put("token", user.getToken());
        long id = database.insert("users", null, contentValues);
        user.setId(id);
        database.close();
        return id;
    }

    public User getUserByLogin(String login) {
        return getUser("login = '" + login + "'");
    }

    public User getUserByToken(String token) {
        return getUser("token = '" + token + "'");
    }

    public User getUserById(long id) {
        return getUser("id = " + id);
    }

    private User getUser(String selection) {
        database = dbHelper.getWritableDatabase();
        return getUser(database.query("users", null, selection, null, null, null, null));
    }

    private User getUser(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int loginColIndex = cursor.getColumnIndex("login");
            int passwordColIndex = cursor.getColumnIndex("password");
            int tokenColIndex = cursor.getColumnIndex("token");
            long id = cursor.getLong(idColIndex);
            String login = cursor.getString(loginColIndex);
            String password = cursor.getString(passwordColIndex);
            String token = cursor.getString(tokenColIndex);
            return new User(id, login, password, token);
        }
        return null;
    }

    //TODO
    public User getActiveUser() {
        return getUser((String) null);
    }

    public int removeAllUsers() {
        database = dbHelper.getWritableDatabase();
        int clearCount = database.delete("users", null, null);
        database.close();
        return clearCount;
    }

    public long update(User user) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", user.getLogin());
        contentValues.put("password", user.getPassword());
        contentValues.put("token", user.getToken());
        long id = database.update("users", contentValues, "id =" + user.getId(), null);
        user.setId(id);
        database.close();
        return id;
    }
}
