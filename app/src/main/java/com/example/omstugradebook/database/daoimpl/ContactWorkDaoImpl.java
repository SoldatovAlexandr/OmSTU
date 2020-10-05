package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.ContactWorkDao;
import com.example.omstugradebook.model.contactwork.ContactWork;

import java.util.ArrayList;
import java.util.List;

public class ContactWorkDaoImpl implements ContactWorkDao {

    @Override
    public int removeAllToContactWork() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete("contact_work", null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //TODO
    @Override
    public List<ContactWork> readContactWorkByUserId(long userId) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query("contact_work", null, "user_id = " + userId, null, null, null, null);
            return getContactWorks(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ContactWork> readAllToContactWork() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query("contact_work", null, null, null, null, null, null);
            return getContactWorks(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<ContactWork> getContactWorks(Cursor cursor) {
        List<ContactWork> contactWorks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int disciplineColIndex = cursor.getColumnIndex("discipline");
            int teacherColIndex = cursor.getColumnIndex("teacher");
            int numberOfTasksColIndex = cursor.getColumnIndex("number_of_tasks");
            int taskLinkColIndex = cursor.getColumnIndex("task_link");
            int userIdColIndex = cursor.getColumnIndex("user_id");
            do {
                String discipline = cursor.getString(disciplineColIndex);
                String teacher = cursor.getString(teacherColIndex);
                String numberOfTasks = cursor.getString(numberOfTasksColIndex);
                String taskLink = cursor.getString(taskLinkColIndex);
                int userId = cursor.getInt(userIdColIndex);
                contactWorks.add(new ContactWork(discipline, teacher, numberOfTasks, taskLink, userId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactWorks;
    }


    @Override
    public boolean insertAllToContactWork(List<ContactWork> contactWorks) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (ContactWork contactWork : contactWorks) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("discipline", contactWork.getDiscipline());
                contentValues.put("teacher", contactWork.getTeacher());
                contentValues.put("number_of_tasks", contactWork.getNumberOfTasks());
                contentValues.put("task_link", contactWork.getTaskLink());
                contentValues.put("user_id", contactWork.getUserId());
                database.insert("contact_work", null, contentValues);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    //TODO
    @Override
    public boolean equalsContactWorks(List<ContactWork> contactWorks) {
        return false;
    }
}
