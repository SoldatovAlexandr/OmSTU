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
    private final static String AUDITORIUM = "auditorium";
    private final static String BEGIN_LESSON = "beginLesson";
    private final static String BUILDING = "building";
    private final static String DATE = "date";
    private final static String DAY_OF_WEEK = "dayOfWeek";
    private final static String DETAIL_INFO = "detailInfo";
    private final static String DISCIPLINE = "discipline";
    private final static String END_LESSON = "endLesson";
    private final static String KIND_OF_WORK = "kindOfWork";
    private final static String LECTURER = "lecturer";
    private final static String STREAM_TYPE = "streamType";
    private final static String DAY_OF_WEEK_STRING = "dayOfWeekString";

    @Override
    public int removeAllSchedule() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            return database.delete(SCHEDULES, null, null);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    public List<Schedule> readScheduleByGroup(String group) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            String selection = "streamType = ?";
            String[] selectionArgs = new String[]{String.valueOf(group)};
            Cursor cursor = database.query(SCHEDULES,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            return getSchedules(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Schedule> readAllSchedule() {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            Cursor cursor = database.query(SCHEDULES,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            return getSchedules(cursor);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertAllSchedule(List<Schedule> schedules) {
        try (DataBaseHelper dbHelper = new DataBaseHelper();
             SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            for (Schedule schedule : schedules) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(AUDITORIUM, schedule.getAuditorium());
                contentValues.put(BEGIN_LESSON, schedule.getBeginLesson());
                contentValues.put(BUILDING, schedule.getBuilding());
                contentValues.put(DATE, schedule.getDate());
                contentValues.put(DAY_OF_WEEK, schedule.getDayOfWeek());
                contentValues.put(DETAIL_INFO, schedule.getDetailInfo());
                contentValues.put(DISCIPLINE, schedule.getDiscipline());
                contentValues.put(END_LESSON, schedule.getEndLesson());
                contentValues.put(KIND_OF_WORK, schedule.getKindOfWork());
                contentValues.put(LECTURER, schedule.getLecturer());
                contentValues.put(STREAM_TYPE, schedule.getStreamType());
                contentValues.put(DAY_OF_WEEK_STRING, schedule.getDayOfWeekString());
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
            int auditoriumColIndex = cursor.getColumnIndex(AUDITORIUM);
            int beginLessonColIndex = cursor.getColumnIndex(BEGIN_LESSON);
            int buildingColIndex = cursor.getColumnIndex(BUILDING);
            int dateColIndex = cursor.getColumnIndex(DATE);
            int dayOfWeekColIndex = cursor.getColumnIndex(DAY_OF_WEEK);
            int detailInfoColIndex = cursor.getColumnIndex(DETAIL_INFO);
            int disciplineColIndex = cursor.getColumnIndex(DISCIPLINE);
            int endLessonColIndex = cursor.getColumnIndex(END_LESSON);
            int kindOfWorkColIndex = cursor.getColumnIndex(KIND_OF_WORK);
            int lecturerColIndex = cursor.getColumnIndex(LECTURER);
            int streamTypeColIndex = cursor.getColumnIndex(STREAM_TYPE);
            int dayOfWeekStringColIndex = cursor.getColumnIndex(DAY_OF_WEEK_STRING);
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
