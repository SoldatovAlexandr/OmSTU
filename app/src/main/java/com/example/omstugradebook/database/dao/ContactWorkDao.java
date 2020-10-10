package com.example.omstugradebook.database.dao;

import com.example.omstugradebook.model.contactwork.ContactWork;

import java.util.List;

public interface ContactWorkDao {
    int removeAllToContactWork();

    int removeAllToContactWorkById(long id);

    List<ContactWork> readContactWorkByUserId(long userId);

    List<ContactWork> readAllToContactWork();

    boolean insertAllToContactWork(List<ContactWork> contactWorks);

    boolean equalsContactWorks(List<ContactWork> contactWorks);
}
