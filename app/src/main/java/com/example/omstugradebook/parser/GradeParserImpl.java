package com.example.omstugradebook.parser;

import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.Student;
import com.example.omstugradebook.model.grade.Subject;
import com.example.omstugradebook.model.grade.SubjectType;
import com.example.omstugradebook.model.grade.Term;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GradeParserImpl {
    public GradeBook getGradeBook(final Document document) {
        Student user = getStudent(document.selectFirst("div.jumbotron.bs-example-bg-classes"));

        List<Term> terms = getTerms(document.select("div.row.tab-pane"));

        return new GradeBook(terms, user);
    }

    private List<Term> getTerms(Elements elements) {
        List<Term> terms = new ArrayList<>();

        int term = 1;

        for (Element element : elements) {
            terms.add(new Term(getPartTerm(element.select("div.col-xs-12"), term)));
            term++;
        }

        return terms;
    }

    private List<Subject> getPartTerm(Elements elements, int term) {
        List<Subject> subjects = new ArrayList<>();

        SubjectType type = null;

        for (Element termElement : elements) {
            if (termElement.text().contains("Экзамены")) {
                type = SubjectType.EXAM;
            } else if (termElement.text().contains("Зачёты")) {
                type = SubjectType.TEST;
            } else if (termElement.text().contains("Курсовые работы")) {
                type = SubjectType.COURSE_WORK;
            } else if (termElement.text().contains("Практики")) {
                type = SubjectType.PRACTICE;
            } else if (termElement.text().contains("Дифференцированный зачет")) {
                type = SubjectType.DIF_TEST;
            }
            subjects.addAll(getSubjectInPartTerm(termElement, term, type));

        }
        return subjects;
    }

    private List<Subject> getSubjectInPartTerm(Element element, int term, SubjectType type) {
        Elements subjectElements = element.select("tr");

        List<Subject> subjects = new ArrayList<>();

        for (Element se : subjectElements) {
            subjects.add(getSubject(se, term, type));
        }

        subjects.remove(0);

        return subjects;
    }

    private Subject getSubject(Element element, int term, SubjectType type) {
        Elements elements = element.select("td");

        if (elements.size() == 0) {
            return null;
        }

        List<Element> elementList = new ArrayList<>(elements);

        String name = elementList.get(0).text().trim();

        String hours = elementList.get(1).text().trim();

        String attendance = null;

        String tempRating = "";

        String mark;

        String date;

        String teacher;

        String toDiploma = "";

        if (elementList.size() == 6) {
            mark = elementList.get(2).text().trim();

            date = elementList.get(3).text().trim();

            teacher = elementList.get(4).text().trim();

            toDiploma = elementList.get(5).text().trim();
        } else if (elementList.size() == 7) {
            attendance = elementList.get(2).text().trim();

            tempRating = elementList.get(3).text().trim();

            mark = elementList.get(4).text().trim();

            date = elementList.get(5).text().trim();

            teacher = elementList.get(6).text().trim();
        } else {
            attendance = elementList.get(2).text().trim();

            tempRating = elementList.get(3).text().trim();

            mark = elementList.get(4).text().trim();

            date = elementList.get(5).text().trim();

            teacher = elementList.get(6).text().trim();

            toDiploma = elementList.get(7).text().trim();
        }
        return new Subject(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, type);
    }

    private Student getStudent(Element element) {
        String fullName = element.selectFirst("h1").text();

        Element elementsUserInfo = element.selectFirst("p");

        String[] strings = elementsUserInfo.toString().split("(</b>|<br>)");

        List<String> userData = new ArrayList<>();

        for (String string : strings) {
            if (!string.contains("<")) {
                userData.add(string);
            }
        }

        return new Student(fullName.trim(), userData.get(0).trim(), userData.get(2).trim(), userData.get(3).trim());
    }
}
