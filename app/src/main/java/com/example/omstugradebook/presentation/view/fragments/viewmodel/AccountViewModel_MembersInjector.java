// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.example.omstugradebook.presentation.view.fragments.viewmodel;

import com.example.omstugradebook.data.dao.ContactWorkDao;
import com.example.omstugradebook.data.dao.ScheduleDao;
import com.example.omstugradebook.data.dao.SubjectDao;
import com.example.omstugradebook.data.dao.UserDao;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AccountViewModel_MembersInjector implements MembersInjector<AccountViewModel> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<ScheduleDao> scheduleDaoProvider;

  private final Provider<ContactWorkDao> contactWorkDaoProvider;

  public AccountViewModel_MembersInjector(
      Provider<UserDao> userDaoProvider,
      Provider<SubjectDao> subjectDaoProvider,
      Provider<ScheduleDao> scheduleDaoProvider,
      Provider<ContactWorkDao> contactWorkDaoProvider) {
    assert userDaoProvider != null;
    this.userDaoProvider = userDaoProvider;
    assert subjectDaoProvider != null;
    this.subjectDaoProvider = subjectDaoProvider;
    assert scheduleDaoProvider != null;
    this.scheduleDaoProvider = scheduleDaoProvider;
    assert contactWorkDaoProvider != null;
    this.contactWorkDaoProvider = contactWorkDaoProvider;
  }

  public static MembersInjector<AccountViewModel> create(
      Provider<UserDao> userDaoProvider,
      Provider<SubjectDao> subjectDaoProvider,
      Provider<ScheduleDao> scheduleDaoProvider,
      Provider<ContactWorkDao> contactWorkDaoProvider) {
    return new AccountViewModel_MembersInjector(
        userDaoProvider, subjectDaoProvider, scheduleDaoProvider, contactWorkDaoProvider);
  }

  @Override
  public void injectMembers(AccountViewModel instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.userDao = userDaoProvider.get();
    instance.subjectDao = subjectDaoProvider.get();
    instance.scheduleDao = scheduleDaoProvider.get();
    instance.contactWorkDao = contactWorkDaoProvider.get();
  }

  public static void injectUserDao(AccountViewModel instance, Provider<UserDao> userDaoProvider) {
    instance.userDao = userDaoProvider.get();
  }

  public static void injectSubjectDao(
      AccountViewModel instance, Provider<SubjectDao> subjectDaoProvider) {
    instance.subjectDao = subjectDaoProvider.get();
  }

  public static void injectScheduleDao(
      AccountViewModel instance, Provider<ScheduleDao> scheduleDaoProvider) {
    instance.scheduleDao = scheduleDaoProvider.get();
  }

  public static void injectContactWorkDao(
      AccountViewModel instance, Provider<ContactWorkDao> contactWorkDaoProvider) {
    instance.contactWorkDao = contactWorkDaoProvider.get();
  }
}
