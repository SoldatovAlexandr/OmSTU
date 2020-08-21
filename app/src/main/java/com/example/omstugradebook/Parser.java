package com.example.omstugradebook;

import com.example.omstugradebook.model.GradeBook;
import com.example.omstugradebook.model.Subject;
import com.example.omstugradebook.model.Term;
import com.example.omstugradebook.model.Student;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public GradeBook getGradeBook(final Document document) {
        Student user = getUser(document.selectFirst("div.jumbotron.bs-example-bg-classes"));
        List<Term> terms = getTerms(document.select("div.row.tab-pane"));
        return new GradeBook(terms, user);
    }

    private Student getUser(Element element) {
        String fullName = element.selectFirst("h1").text();
        Element elementsUserInfo = element.selectFirst("p");
        String[] strings = elementsUserInfo.toString().split("(</b>|<br>)");
        List<String> userData = new ArrayList<>();
        for (String string : strings) {
            if (!string.contains("<")) {
                userData.add(string);
            }
        }
        return new Student(fullName, userData.get(0), userData.get(2), userData.get(3));
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
        int type = -1;
        for (Element termElement : elements) {
            if (termElement.text().contains("Экзамены")) {
                type = 0;
            } else if (termElement.text().contains("Зачёты")) {
                type = 1;
            } else if (termElement.text().contains("Курсовые работы")) {
                type = 2;
            } else if (termElement.text().contains("Практики")) {
                type = 3;
            } else if (termElement.text().contains("Дифференцированный зачет")) {
                type = 4;
            }
            subjects.addAll(getSubjectInPartTerm(termElement, term, type));
        }
        return subjects;
    }

    private List<Subject> getSubjectInPartTerm(Element element, int term, int type) {
        Elements subjectElements = element.select("tr");
        List<Subject> subjects = new ArrayList<>();
        for (Element se : subjectElements) {
            subjects.add(getSubject(se, term, type));
        }
        subjects.remove(0);
        return subjects;
    }

    private Subject getSubject(Element element, int term, int type) {
        Elements elements = element.select("td");
        if (elements.size() == 0) {
            return null;
        }
        List<Element> elementList = new ArrayList<>(elements);
        String name = elementList.get(0).text();
        String hours = elementList.get(1).text();
        String attendance = null;
        String tempRating = "";
        String mark;
        String date;
        String teacher;
        String toDiploma = "";
        if (elementList.size() == 6) {
            mark = elementList.get(2).text();
            date = elementList.get(3).text();
            teacher = elementList.get(4).text();
            toDiploma = elementList.get(5).text();
        } else if (elementList.size() == 7) {
            attendance = elementList.get(2).text();
            tempRating = elementList.get(3).text();
            mark = elementList.get(4).text();
            date = elementList.get(5).text();
            teacher = elementList.get(6).text();
        } else {
            attendance = elementList.get(2).text();
            tempRating = elementList.get(3).text();
            mark = elementList.get(4).text();
            date = elementList.get(5).text();
            teacher = elementList.get(6).text();
            toDiploma = elementList.get(7).text();
        }
        return new Subject(name, hours, attendance, tempRating, mark, date, teacher, toDiploma, term, type);
    }
}
