package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.SubjectType;

import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao {

    public SubjectDaoImpl() {
    }

    @Override
    public int removeAllSubjects() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete("subjects", null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Subject> readSubjectsByTerm(int termFilter) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query("subjects", null, "term = " + termFilter, null, null, null, null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Subject> readAllSubjects() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query("subjects", null, null, null, null, null, null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertAllSubjects(List<Subject> subjects) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (Subject subject : subjects) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", subject.getName());
                contentValues.put("hours", subject.getHours());
                contentValues.put("attendance", subject.getAttendance());
                contentValues.put("tempRating", subject.getTempRating());
                contentValues.put("mark", subject.getMark());
                contentValues.put("date", subject.getDate());
                contentValues.put("teacher", subject.getTeacher());
                contentValues.put("toDiploma", subject.getToDiploma());
                contentValues.put("term", subject.getTerm());
                contentValues.put("type", subject.getType().ordinal());
                contentValues.put("user_id", subject.getUserId());
                database.insert("subjects", null, contentValues);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equalsSubjects(List<Subject> subjects) {
        List<Subject> dataBaseSubjects = readAllSubjects();
        return dataBaseSubjects.equals(subjects);
    }

    @Override
    public int getCountTerm() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selectQuery = "SELECT max(term) as term FROM subjects";
            Cursor cursor = database.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex("term"));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long getCount() {
        return readAllSubjects().size();
    }//TODO

    //TODO метод не работает
    @Override
    public List<Subject> readSubjectsByUser(long userId) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selection = "user_id = ?";
            String[] selectionArgs = new String[]{String.valueOf(userId)};
            Cursor cursor = database.query("subjects", null, selection, selectionArgs, null, null, null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Subject> getSubjects(Cursor cursor) {
        List<Subject> subjects = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex("name");
            int hoursColIndex = cursor.getColumnIndex("hours");
            int attendanceColIndex = cursor.getColumnIndex("attendance");
            int tempRatingColIndex = cursor.getColumnIndex("tempRating");
            int markColIndex = cursor.getColumnIndex("mark");
            int dateColIndex = cursor.getColumnIndex("date");
            int teacherColIndex = cursor.getColumnIndex("teacher");
            int toDiplomaColIndex = cursor.getColumnIndex("toDiploma");
            int termColIndex = cursor.getColumnIndex("term");
            int typeColIndex = cursor.getColumnIndex("type");
            int userIdColIndex = cursor.getColumnIndex("user_id");
            do {
                String name = cursor.getString(nameColIndex);
                String hours = cursor.getString(hoursColIndex);
                String attendance = cursor.getString(attendanceColIndex);
                String tempRating = cursor.getString(tempRatingColIndex);
                String mark = cursor.getString(markColIndex);
                String date = cursor.getString(dateColIndex);
                String teacher = cursor.getString(teacherColIndex);
                String toDiploma = cursor.getString(toDiplomaColIndex);
                int term = cursor.getInt(termColIndex);
                int type = cursor.getInt(typeColIndex);
                int userId = cursor.getInt(userIdColIndex);
                subjects.add(new Subject(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, SubjectType.values()[type], userId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }
}
