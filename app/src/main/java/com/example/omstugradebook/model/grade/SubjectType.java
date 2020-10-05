package com.example.omstugradebook.model.grade;

public enum SubjectType {
    EXAM("Экзамены"),
    TEST("Зачеты"),
    PRACTICE("Практики"),
    COURSE_WORK("Курсовые работы"),
    DIF_TEST("Дифференцированные зачеты");
    private final String textString;

    SubjectType(String textString) {
        this.textString = textString;
    }

    public String getTextString() {
        return textString;
    }
}
