package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

@RequestMapping(path = "/users")
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAll() {
        log.info("GET request on path: /users");
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserDto getById(@Positive @PathVariable("userId") Integer id) {
        log.info("GET request on path: /users/{}", id);
        return userService.getById(id);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto user) {
        log.info("POST request on path: /users");
        return userService.create(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@Positive @PathVariable("userId") Integer id, @RequestBody UserDto userDto) {
        log.info("PATCH request on path: /users/{}", id);
        return userService.update(userDto, id);
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@Positive @PathVariable("userId") Integer id) {
        log.info("DELETE request on path: /users/{}", id);
        return userService.delete(id);
    }
}
