package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.contactwork.ContactWork;

import java.util.List;

public interface ContactWorkDao {
    int removeAllToContactWork();

    List<ContactWork> readAllToContactWork();

    boolean insertAllToContactWork(List<ContactWork> contactWorks);

}
