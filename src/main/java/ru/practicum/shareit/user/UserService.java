package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserStorage userStorage;

    public Collection<UserDto> getAll() {
        return userStorage.getAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userStorage.create(user));
    }


    public UserDto update(UserDto userDto, Integer id) {
        userStorage.validateId(id);
        User user = userStorage.getById(id);
        userDto.setId(id);
        userDto.setEmail(userDto.getEmail() == null ? user.getEmail() : userDto.getEmail());
        userDto.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        User updUser = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userStorage.update(updUser));
    }

    public UserDto delete(Integer id) {
        return UserMapper.toUserDto(userStorage.deleteById(id));
    }

    public UserDto getById(int id) {
        return UserMapper.toUserDto(userStorage.getById(id));
    }
}
