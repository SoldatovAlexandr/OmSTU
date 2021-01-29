package com.example.omstugradebook.domain.interactor;

import com.example.omstugradebook.data.repository.UserRepository;

public class StartInteractor {

    private final UserRepository userRepository;

    public StartInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasUser() {
        return userRepository.getUser();
    }
}
