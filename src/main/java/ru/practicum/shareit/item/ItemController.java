package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDto getById(@Positive @PathVariable("id") Integer id) {
        log.info("GET request on path: {}/{}", "/items", id);
        return itemService.getById(id);
    }

    @PostMapping
    public ItemDto add(@Positive @RequestHeader("X-Sharer-User-Id") Integer userId, @Valid @RequestBody ItemDto item) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.add(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@Positive @RequestHeader("X-Sharer-User-Id") int userId,
                          @RequestBody ItemDto item, @Positive @PathVariable("itemId") Integer itemId) {
        log.info("PATCH request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.update(item, userId, itemId);
    }

    @GetMapping
    public Collection<ItemDto> getByUserId(@Positive @RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/items", userId);
        return itemService.getByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(
            @RequestParam("text") String text, @Positive @RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("GET request on path: {}, X-Sharer-User-Id = {}", "/search", userId);
        return itemService.search(text, userId);
    }


}
