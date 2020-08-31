package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.SubjectType;
import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.SubjectDao;
import com.example.omstugradebook.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public SubjectDaoImpl(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    @Override
    public int removeAllSubjects() {
        database = dbHelper.getWritableDatabase();
        int clearCount = database.delete("subjects", null, null);
        database.close();
        return clearCount;
    }

    @Override
    public List<Subject> readSubjectsByTerm(int termFilter) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query("subjects", null, "term = " + termFilter, null, null, null, null);
        return getSubjects(cursor);
    }

    @Override
    public List<Subject> readAllSubjects() {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query("subjects", null, null, null, null, null, null);
        return getSubjects(cursor);
    }

    @Override
    public void insertAllSubjects(List<Subject> subjects) {
        database = dbHelper.getWritableDatabase();
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
            database.insert("subjects", null, contentValues);
        }
        database.close();
    }

    @Override
    public boolean equalsSubjects(List<Subject> subjects) {
        List<Subject> dataBaseSubjects = readAllSubjects();
        return dataBaseSubjects.equals(subjects);
    }

    @Override
    public int getCountTerm() {
        String selectQuery = "SELECT max(term) as term FROM subjects";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("term"));
    }

    @Override
    public long getCount() {
        return readAllSubjects().size();
    }//TODO

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
                subjects.add(new Subject(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, SubjectType.values()[type]));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return subjects;
    }
}
