package com.example.omstugradebook.module;

import com.example.omstugradebook.ScheduleTypeAutoComponentAdapter;
import com.example.omstugradebook.service.AuthService;
import com.example.omstugradebook.service.ContactWorkService;
import com.example.omstugradebook.service.ScheduleService;
import com.example.omstugradebook.view.activity.viewmodel.LoginViewModel;
import com.example.omstugradebook.view.activity.viewmodel.SearchViewModel;
import com.example.omstugradebook.view.activity.viewmodel.StartViewModel;
import com.example.omstugradebook.view.fragments.viewmodel.AccountViewModel;
import com.example.omstugradebook.view.fragments.viewmodel.ContactWorkViewModel;
import com.example.omstugradebook.view.fragments.viewmodel.GradeViewModel;
import com.example.omstugradebook.view.fragments.viewmodel.ScheduleViewModel;

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

    void injectContactWorkService(ContactWorkService contactWorkService);

    void injectAuthService(AuthService authService);
}
