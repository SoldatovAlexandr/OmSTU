package com.example.omstugradebook.view.activity.model;

import com.example.omstugradebook.model.contactwork.ContactWorksTask;

import java.util.List;
import java.util.Objects;

public class ContactWorkTasksModel {
    private final List<ContactWorksTask> contactWorksTasks;

    public ContactWorkTasksModel(List<ContactWorksTask> contactWorksTasks) {
        this.contactWorksTasks = contactWorksTasks;
    }

    public List<ContactWorksTask> getContactWorksTasks() {
        return contactWorksTasks;
    }

    @Override
    public String toString() {
        return "ContactWorkModel{" +
                "contactWorksTasks=" + contactWorksTasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactWorkTasksModel that = (ContactWorkTasksModel) o;
        return Objects.equals(contactWorksTasks, that.contactWorksTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactWorksTasks);
    }
}
