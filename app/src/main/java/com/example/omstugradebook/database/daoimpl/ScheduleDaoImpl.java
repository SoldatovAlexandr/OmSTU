package com.example.omstugradebook.database.daoimpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omstugradebook.database.DataBaseHelper;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.model.schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoImpl implements ScheduleDao {
    private final static String SCHEDULES = "schedules";

    @Override
    public int removeAllSchedule() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete(SCHEDULES, null, null);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    public List<Schedule> readScheduleByGroup(String group) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(SCHEDULES, null, "streamType = '" + group + "'", null, null, null, null);
            return getSchedules(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Schedule> readAllSchedule() {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(SCHEDULES, null, null, null, null, null, null);
            return getSchedules(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertAllSchedule(List<Schedule> schedules) {
        try (DataBaseHelper dbHelper = new DataBaseHelper(); SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (Schedule schedule : schedules) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("auditorium", schedule.getAuditorium());
                contentValues.put("beginLesson", schedule.getBeginLesson());
                contentValues.put("building", schedule.getBuilding());
                contentValues.put("date", schedule.getDate());
                contentValues.put("dayOfWeek", schedule.getDayOfWeek());
                contentValues.put("detailInfo", schedule.getDetailInfo());
                contentValues.put("discipline", schedule.getDiscipline());
                contentValues.put("endLesson", schedule.getEndLesson());
                contentValues.put("kindOfWork", schedule.getKindOfWork());
                contentValues.put("lecturer", schedule.getLecturer());
                contentValues.put("streamType", schedule.getStreamType());
                contentValues.put("dayOfWeekString", schedule.getDayOfWeekString());
                database.insert(SCHEDULES, null, contentValues);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equalsSchedule(List<Schedule> schedules) {
        return false;
    }

    private List<Schedule> getSchedules(Cursor cursor) {
        List<Schedule> schedules = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int auditoriumColIndex = cursor.getColumnIndex("auditorium");
            int beginLessonColIndex = cursor.getColumnIndex("beginLesson");
            int buildingColIndex = cursor.getColumnIndex("building");
            int dateColIndex = cursor.getColumnIndex("date");
            int dayOfWeekColIndex = cursor.getColumnIndex("dayOfWeek");
            int detailInfoColIndex = cursor.getColumnIndex("detailInfo");
            int disciplineColIndex = cursor.getColumnIndex("discipline");
            int endLessonColIndex = cursor.getColumnIndex("endLesson");
            int kindOfWorkColIndex = cursor.getColumnIndex("kindOfWork");
            int lecturerColIndex = cursor.getColumnIndex("lecturer");
            int streamTypeColIndex = cursor.getColumnIndex("streamType");
            int dayOfWeekStringColIndex = cursor.getColumnIndex("dayOfWeekString");
            do {
                String auditorium = cursor.getString(auditoriumColIndex);
                String beginLesson = cursor.getString(beginLessonColIndex);
                String building = cursor.getString(buildingColIndex);
                String detailInfo = cursor.getString(detailInfoColIndex);
                String discipline = cursor.getString(disciplineColIndex);
                String endLesson = cursor.getString(endLessonColIndex);
                String kindOfWork = cursor.getString(kindOfWorkColIndex);
                String lecturer = cursor.getString(lecturerColIndex);
                String streamType = cursor.getString(streamTypeColIndex);
                String dayOfWeekString = cursor.getString(dayOfWeekStringColIndex);
                String date = cursor.getString(dateColIndex);
                int dayOfWeek = cursor.getInt(dayOfWeekColIndex);
                schedules.add(new Schedule(
                        auditorium,
                        beginLesson,
                        building,
                        date,
                        dayOfWeek,
                        detailInfo,
                        discipline,
                        endLesson,
                        kindOfWork,
                        lecturer,
                        streamType,
                        dayOfWeekString
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return schedules;
    }
}
