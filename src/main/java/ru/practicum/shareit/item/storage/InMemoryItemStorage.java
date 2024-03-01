package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();
    private int nextId = 1;

    @Override
    public Item add(Item item) {
        item.setId(nextId);
        items.put(nextId, item);
        nextId++;
        log.info("item id = {} added", item.getId());
        return item;
    }

    @Override
    public Item update(Item item) {
        int id = item.getId();
        log.info("item id = {} updated", item.getId());
        return items.put(id, item);
    }

    @Override
    public Item getById(int id) {
        return items.get(id);
    }

    @Override
    public List<Item> getByUser(int userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        items.remove(id);
        log.info("item id = {} removed", id);
    }

    @Override
    public List<Item> search(String text) {
        return items.values().stream()
                .filter(Item::isAvailable)
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase()) ||
                        item.getName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void validateId(int id) {
        if (!items.containsKey(id)) {
            throw new NoSuchElementException(String.format("Item id = %d not found", id));
        }
    }
}
