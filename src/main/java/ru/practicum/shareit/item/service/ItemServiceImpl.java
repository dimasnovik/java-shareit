package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.WrongUserAccessException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto getById(int id) {
        itemStorage.validateId(id);
        Item item = itemStorage.getById(id);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto add(ItemDto itemDto, int userId) {
        userStorage.validateId(userId);
        Item item = ItemMapper.toItem(itemDto, userStorage.getById(userId));
        Item savedItem = itemStorage.add(item);
        log.info("item id = {} added", savedItem.getId());
        return ItemMapper.toItemDto(savedItem);
    }

    @Override
    public ItemDto update(ItemDto itemDto, int userId, int itemId) {
        itemStorage.validateId(itemId);
        userStorage.validateId(userId);
        User owner = userStorage.getById(userId);
        Item oldItem = itemStorage.getById(itemId);
        if (!oldItem.getOwner().equals(owner)) {
            throw new WrongUserAccessException(
                    String.format("Access denied. expected userId = %d, actual userId = %d", oldItem.getOwner().getId(), userId));
        }
        itemDto.setId(itemId);
        itemDto.setName(itemDto.getName() == null ? oldItem.getName() : itemDto.getName());
        itemDto.setDescription(itemDto.getDescription() == null ? oldItem.getDescription() : itemDto.getDescription());
        itemDto.setAvailable(itemDto.getAvailable() == null ? oldItem.isAvailable() : itemDto.getAvailable());
        Item item = itemStorage.update(ItemMapper.toItem(itemDto, owner));
        log.info("item id = {} updated", item.getId());
        return itemDto;
    }

    @Override
    public Collection<ItemDto> getByUserId(int userId) {
        return itemStorage.getByUser(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return itemStorage.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
