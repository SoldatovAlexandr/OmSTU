package com.example.omstugradebook.data.parser;

import com.example.omstugradebook.data.model.contactwork.ContactWork;
import com.example.omstugradebook.data.model.contactwork.ContactWorksTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ContactWorkParser {

    public List<ContactWork> getContactWorks(Document document) {
        return shareContactWorks(document.selectFirst("tbody"));
    }

    public List<ContactWorksTask> getTasks(Document doc) {
        Element element = doc.selectFirst("div.container");

        Elements elements = element.select("tr");

        List<ContactWorksTask> contactWorksTasks = new ArrayList<>();

        for (int i = 1; i < elements.size(); i++) {
            contactWorksTasks.add(getContactWorksTask(elements.get(i)));
        }

        return contactWorksTasks;
    }

    private ContactWorksTask getContactWorksTask(Element element) {
        List<Element> elementList = new ArrayList<>(element.select("td"));

        int number = Integer.parseInt(elementList.get(0).text());

        String comment = elementList.get(1).text();

        String file = elementList.get(2).text();

        Element linkElement = elementList.get(2).selectFirst("a");

        String link = "";

        if (linkElement != null) {
            link = linkElement.attr("href");
        }

        String date = elementList.get(3).text();

        String teacher = elementList.get(4).text();
        //тут пиздец
        return new ContactWorksTask(number, comment, teacher, file, date, link);
    }

    private List<ContactWork> shareContactWorks(Element element) {
        Elements elements = element.select("tr");

        List<ContactWork> contactWorks = new ArrayList<>();

        for (int i = 1; i < elements.size(); i++) {
            contactWorks.add(getContactWork(elements.get(i)));
        }

        return contactWorks;
    }

    private ContactWork getContactWork(Element element) {
        List<Element> elementList = new ArrayList<>(element.select("td"));

        String discipline = elementList.get(0).text();

        String teacher = elementList.get(1).text();

        String numberOfTasks = elementList.get(2).text();

        Element link = elementList.get(3).selectFirst("a");

        String url = link.attr("href");

        return new ContactWork(discipline, teacher, numberOfTasks, url);
    }
}
