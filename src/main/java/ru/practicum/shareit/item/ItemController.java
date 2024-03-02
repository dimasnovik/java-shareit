package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {
    private final ItemService itemService;
    private final String userIdHeaderName = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ItemDto getById(@Positive @PathVariable("id") int id) {
        log.info("GET request on path: {}/{}", "/items", id);
        return itemService.getById(id);
    }

    @PostMapping
    public ItemDto add(@Positive @RequestHeader(userIdHeaderName) int userId, @Valid @RequestBody ItemDto item) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.add(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@Positive @RequestHeader(userIdHeaderName) int userId,
                          @RequestBody ItemDto item, @Positive @PathVariable("itemId") int itemId) {
        log.info("PATCH request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.update(item, userId, itemId);
    }

    @GetMapping
    public Collection<ItemDto> getByUserId(@Positive @RequestHeader(userIdHeaderName) int userId) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.getByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(
            @RequestParam("text") String text, @Positive @RequestHeader(userIdHeaderName) int userId) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/search", userId);
        return itemService.search(text);
    }


}
