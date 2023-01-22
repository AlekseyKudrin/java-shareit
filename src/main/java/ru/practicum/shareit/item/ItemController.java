package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Optional<ItemDto> create(
            @RequestHeader Map<String, String> headers,
            @Valid @RequestBody ItemDto itemDto
    ) {
        log.info("Creating item");
        return itemService.create(headers, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Optional<ItemDto> update(
            @RequestHeader Map<String, String> headers,
            @PathVariable int itemId,
            @RequestBody ItemDto itemDto
    ) {
        log.info("Updating item");
        return itemService.update(headers, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public Optional<ItemDto> get(
            @PathVariable int itemId) {
        log.info("Return item by id");
        return itemService.get(itemId);
    }

    @GetMapping
    public Collection<ItemDto> getAll(
            @RequestHeader Map<String, String> headers
    ) {
        log.info("Return item list");
        return itemService.getAll(headers);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(
            @RequestParam @NotBlank String text
    ) {
        log.info("Return of found items");
        return itemService.search(text);
    }
}
