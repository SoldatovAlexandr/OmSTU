package com.example.omstugradebook.presentation.view.fragments.model;

import com.example.omstugradebook.data.model.contactwork.ContactWork;

import java.util.List;
import java.util.Objects;

public class ContactWorkModel {
    private final List<ContactWork> contactWorks;

    public ContactWorkModel(List<ContactWork> contactWorks) {
        this.contactWorks = contactWorks;
    }

    public List<ContactWork> getContactWorks() {
        return contactWorks;
    }

    @Override
    public String toString() {
        return "ContactWorkModel{" +
                "contactWorks=" + contactWorks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactWorkModel that = (ContactWorkModel) o;
        return Objects.equals(contactWorks, that.contactWorks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactWorks);
    }
}
