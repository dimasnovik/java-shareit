package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.user.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private int nextId = 1;

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        validateEmail(user.getEmail());
        user.setId(nextId);
        nextId++;
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        log.info("User with id = {} created", user.getId());
        return user;
    }

    @Override
    public User getById(int id) {
        return users.get(id);
    }

    @Override
    public User update(User user) {
        User oldUser = users.get(user.getId());
        if (!user.getEmail().equals(oldUser.getEmail())) {
            validateEmail(user.getEmail());
        }
        emails.remove(oldUser.getEmail());
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        log.info("User with id = {} updated", user.getId());

        return user;
    }

    @Override
    public User deleteById(int id) {
        log.info("User with id = {} deleted", id);
        String email = users.get(id).getEmail();
        emails.remove(email);
        return users.remove(id);
    }

    @Override
    public void validateId(int id) {
        if (!users.containsKey(id)) {
            log.warn("No user with id = " + id);
            throw new NoSuchElementException("No user with id = " + id);
        }
    }

    private void validateEmail(String email) {
        if (emails.contains(email)) {
            throw new DuplicateEmailException("Email already exists:" + email);
        }
    }
}
