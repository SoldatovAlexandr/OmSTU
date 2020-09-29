package com.example.omstugradebook.database.dao;

import android.content.Context;

import com.example.omstugradebook.model.contactwork.ContactWork;

import java.util.List;

public interface ContactWorkDao {
    int removeAllToContactWork(Context context);

    List<ContactWork> readContactWorkByUserId(int userId, Context context);

    List<ContactWork> readAllToContactWork(Context context);

    boolean insertAllToContactWork(List<ContactWork> contactWorks, Context context);

    boolean equalsContactWorks(List<ContactWork> contactWorks, Context context);
}
