package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestBody ItemRequestDto itemRequestDto
            ) {
        log.info("Creating item request");
        return itemRequestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsOwner(
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        log.info("Return request list owner");
        return itemRequestService.getRequestsOwner(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAll(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam long from, long size
    ) {
        log.info("Return all request list");
        return itemRequestService.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getById(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long requestId
    ) {
        log.info("Return request by id");
        return itemRequestService.getById(userId, requestId);
    }
}
