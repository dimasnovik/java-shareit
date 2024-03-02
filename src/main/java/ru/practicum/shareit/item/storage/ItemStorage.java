package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item add(Item item);

    Item update(Item item);

    Item getById(int id);

    List<Item> getByUser(int userId);

    void deleteById(int id);

    List<Item> search(String text);

    void validateId(int id);
}
