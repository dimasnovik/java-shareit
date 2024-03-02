package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto getById(int id);

    ItemDto add(ItemDto itemDto, int userId);

    ItemDto update(ItemDto itemDto, int userId, int itemId);

    Collection<ItemDto> getByUserId(int userId);

    Collection<ItemDto> search(String text);
}
