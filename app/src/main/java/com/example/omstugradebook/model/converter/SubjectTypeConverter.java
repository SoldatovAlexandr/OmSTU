package com.example.omstugradebook.model.converter;

import androidx.room.TypeConverter;

import com.example.omstugradebook.model.grade.SubjectType;

public class SubjectTypeConverter {
    @TypeConverter
    public int fromSubjectType(SubjectType subjectType) {
        return subjectType.ordinal();
    }

    @TypeConverter
    public SubjectType toSubjectType(int ordinal) {
        return SubjectType.values()[ordinal];
    }
}
