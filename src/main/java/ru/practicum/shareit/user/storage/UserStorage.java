package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAll();

    User create(User user);

    User getById(int id);

    User update(User user);

    User deleteById(int id);

    void validateId(int id);

}
