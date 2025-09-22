package com.expense.tracker.user.internal.service;

import com.expense.tracker.core.event.UserCreatedEvent;
import com.expense.tracker.user.internal.entity.User;
import com.expense.tracker.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public UserService(UserRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public User createOrUpdateUser(String googleId, String email, String name){
        User user = repository.findByGoogleId(googleId)
                .orElseGet(() -> repository.findByEmail(email).orElse(null));

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setGoogleId(googleId);
        } else {
            user.setName(name);
            user.setGoogleId(googleId);
        }

        return createUser(user);
    }

    public User createUser(User user) {
        User savedUser =  repository.save(user);

        eventPublisher.publishEvent(new UserCreatedEvent(savedUser.getUserId()));

        return savedUser;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
}
