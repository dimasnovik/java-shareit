package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
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
        return user;
    }

    @Override
    public User deleteById(int id) {
        String email = users.get(id).getEmail();
        emails.remove(email);
        return users.remove(id);
    }

    @Override
    public void validateId(int id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException("No user with id = " + id);
        }
    }

    private void validateEmail(String email) {
        if (emails.contains(email)) {
            throw new DuplicateEmailException("Email already exists:" + email);
        }
    }
}
