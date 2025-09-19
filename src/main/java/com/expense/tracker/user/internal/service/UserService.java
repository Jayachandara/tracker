package com.expense.tracker.user.internal.service;

import com.expense.tracker.user.internal.entity.User;
import com.expense.tracker.user.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
}
