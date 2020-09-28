package com.example.omstugradebook.parser.iface;

import com.example.omstugradebook.model.grade.GradeBook;

import org.jsoup.nodes.Document;

public interface GradeParser {
    GradeBook getGradeBook(final Document document);
}
