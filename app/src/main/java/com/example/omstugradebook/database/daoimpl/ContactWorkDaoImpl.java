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
    private static final String CONTACT_WORK = "contact_work";
    private static final String DISCIPLINE = "discipline";
    private static final String TEACHER = "teacher";
    private static final String NUMBER_OF_TASKS = "number_of_tasks";
    private static final String TASK_LINK = "task_link";

    @Override
    public int removeAllToContactWork() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete(CONTACT_WORK, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public List<ContactWork> readAllToContactWork() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(CONTACT_WORK,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            return getContactWorks(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<ContactWork> getContactWorks(Cursor cursor) {
        List<ContactWork> contactWorks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int disciplineColIndex = cursor.getColumnIndex(DISCIPLINE);
            int teacherColIndex = cursor.getColumnIndex(TEACHER);
            int numberOfTasksColIndex = cursor.getColumnIndex(NUMBER_OF_TASKS);
            int taskLinkColIndex = cursor.getColumnIndex(TASK_LINK);
            do {
                String discipline = cursor.getString(disciplineColIndex);
                String teacher = cursor.getString(teacherColIndex);
                String numberOfTasks = cursor.getString(numberOfTasksColIndex);
                String taskLink = cursor.getString(taskLinkColIndex);
                contactWorks.add(new ContactWork(discipline, teacher, numberOfTasks, taskLink));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactWorks;
    }


    @Override
    public boolean insertAllToContactWork(List<ContactWork> contactWorks) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (ContactWork contactWork : contactWorks) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DISCIPLINE, contactWork.getDiscipline());
                contentValues.put(TEACHER, contactWork.getTeacher());
                contentValues.put(NUMBER_OF_TASKS, contactWork.getNumberOfTasks());
                contentValues.put(TASK_LINK, contactWork.getTaskLink());
                database.insert(CONTACT_WORK, null, contentValues);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
