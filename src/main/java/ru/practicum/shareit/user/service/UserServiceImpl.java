package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public Collection<UserDto> getAll() {
        return userStorage.getAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userStorage.create(UserMapper.toUser(userDto));
        log.info("User with id = {} created", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(UserDto userDto, Integer id) {
        userStorage.validateId(id);
        User user = userStorage.getById(id);
        userDto.setId(id);
        userDto.setEmail(userDto.getEmail() == null ? user.getEmail() : userDto.getEmail());
        userDto.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        User updUser = userStorage.update(UserMapper.toUser(userDto));
        log.info("User with id = {} updated", updUser.getId());
        return UserMapper.toUserDto(updUser);
    }

    @Override
    public UserDto delete(Integer id) {
        userStorage.validateId(id);
        User user = userStorage.deleteById(id);
        log.info("User with id = {} deleted", id);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(int id) {
        userStorage.validateId(id);
        return UserMapper.toUserDto(userStorage.getById(id));
    }
}
