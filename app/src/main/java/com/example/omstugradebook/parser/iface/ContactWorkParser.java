package com.example.omstugradebook.parser.iface;

import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ContactWorkParser {

    List<ContactWork> getContactWorks(Document document, int userId);

    List<ContactWorksTask> getTasks(Document doc);
}
