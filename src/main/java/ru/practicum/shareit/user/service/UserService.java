package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto, Integer id);

    UserDto delete(Integer id);

    UserDto getById(int id);
}
