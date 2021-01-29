package com.example.omstugradebook.presentation.module;

import com.example.omstugradebook.ScheduleTypeAutoComponentAdapter;
import com.example.omstugradebook.data.repository.UserRepository;
import com.example.omstugradebook.domain.connector.AuthService;
import com.example.omstugradebook.domain.connector.Connector;
import com.example.omstugradebook.domain.connector.ScheduleService;
import com.example.omstugradebook.domain.interactor.LoginInteractor;
import com.example.omstugradebook.presentation.view.activity.viewmodel.ContactWorkTasksViewModel;
import com.example.omstugradebook.presentation.view.activity.viewmodel.LoginViewModel;
import com.example.omstugradebook.presentation.view.activity.viewmodel.SearchViewModel;
import com.example.omstugradebook.presentation.view.activity.viewmodel.StartViewModel;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.AccountViewModel;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.ContactWorkViewModel;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.GradeViewModel;
import com.example.omstugradebook.presentation.view.fragments.viewmodel.ScheduleViewModel;

import dagger.Component;

@Component(modules = {StorageModule.class})
public interface AppComponent {
    void injectContactWorkViewModel(ContactWorkViewModel contactWorkViewModel);

    void injectAccountViewModel(AccountViewModel accountViewModel);

    void injectGradeViewModel(GradeViewModel gradeViewModel);

    void injectScheduleViewModel(ScheduleViewModel scheduleViewModel);

    void injectLoginViewModel(LoginViewModel loginViewModel);

    void injectSearchViewModel(SearchViewModel searchViewModel);

    void injectStartViewModel(StartViewModel startViewModel);

    void injectScheduleService(ScheduleService scheduleService);

    void injectScheduleTypeAutoComponentAdapter(ScheduleTypeAutoComponentAdapter adapter);

    void injectConnector(Connector contactWorkService);

    void injectAuthService(AuthService authService);

    void injectContactWorkTaskViewModel(ContactWorkTasksViewModel contactWorkTasksViewModel);

    void injectStartRepository(UserRepository userRepository);

    void injectLoginInteractor(LoginInteractor loginInteractor);
}
