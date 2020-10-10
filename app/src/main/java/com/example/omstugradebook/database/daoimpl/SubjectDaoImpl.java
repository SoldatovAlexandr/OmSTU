package com.example.omstugradebook.database.daoimpl;

import android.annotation.SuppressLint;
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
    private final static String SUBJECTS = "subjects";
    private final static String NAME = "name";
    private final static String HOURS = "hours";
    private final static String ATTENDANCE = "attendance";
    private final static String TEMP_RATING = "tempRating";
    private final static String MARK = "mark";
    private final static String DATE = "date";
    private final static String TEACHER = "teacher";
    private final static String TO_DIPLOMA = "toDiploma";
    private final static String TERM = "term";
    private final static String TYPE = "type";
    private final static String USER_ID = "user_id";

    @Override
    public int removeAllSubjects() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete(SUBJECTS, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int removeSubjectsById(long id) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String whereClause = "user_id = ?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            return database.delete(SUBJECTS, whereClause, whereArgs);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Subject> readSubjectsByTerm(int termFilter) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selection = "term = ?";
            String[] selectionArgs = new String[]{String.valueOf(termFilter)};
            Cursor cursor = database.query(SUBJECTS,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Subject> readAllSubjects() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(SUBJECTS,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertAllSubjects(List<Subject> subjects) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (Subject subject : subjects) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(NAME, subject.getName());
                contentValues.put(HOURS, subject.getHours());
                contentValues.put(ATTENDANCE, subject.getAttendance());
                contentValues.put(TEMP_RATING, subject.getTempRating());
                contentValues.put(MARK, subject.getMark());
                contentValues.put(DATE, subject.getDate());
                contentValues.put(TEACHER, subject.getTeacher());
                contentValues.put(TO_DIPLOMA, subject.getToDiploma());
                contentValues.put(TERM, subject.getTerm());
                contentValues.put(TYPE, subject.getType().ordinal());
                contentValues.put(USER_ID, subject.getUserId());
                database.insert(SUBJECTS, null, contentValues);
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
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selectQuery = "SELECT max(term) as term FROM subjects";
            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(TERM));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long getCount() {
        return readAllSubjects().size();
    }//TODO

    @Override
    public List<Subject> readSubjectsByUser(long userId) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selection = "user_id = ?";
            String[] selectionArgs = new String[]{String.valueOf(userId)};
            Cursor cursor = database.query(SUBJECTS,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            return getSubjects(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Subject> getSubjects(Cursor cursor) {
        List<Subject> subjects = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex(NAME);
            int hoursColIndex = cursor.getColumnIndex(HOURS);
            int attendanceColIndex = cursor.getColumnIndex(ATTENDANCE);
            int tempRatingColIndex = cursor.getColumnIndex(TEMP_RATING);
            int markColIndex = cursor.getColumnIndex(MARK);
            int dateColIndex = cursor.getColumnIndex(DATE);
            int teacherColIndex = cursor.getColumnIndex(TEACHER);
            int toDiplomaColIndex = cursor.getColumnIndex(TO_DIPLOMA);
            int termColIndex = cursor.getColumnIndex(TERM);
            int typeColIndex = cursor.getColumnIndex(TYPE);
            int userIdColIndex = cursor.getColumnIndex(USER_ID);
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
                subjects.add(new Subject(name, hours, attendance, tempRating, mark, date, teacher,
                        toDiploma, term, SubjectType.values()[type], userId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }
}
