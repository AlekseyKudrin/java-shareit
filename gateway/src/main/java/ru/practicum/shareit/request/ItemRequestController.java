package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Validated(ItemRequestDto.Create.class) @RequestBody ItemRequestDto itemRequestDto
    ) {
        log.info("Creating item request");
        return itemRequestClient.createRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestsOwner(
            @RequestHeader("X-Sharer-User-Id") long userId
    ) {
        log.info("Return request list owner");
        return itemRequestClient.getRequestsOwner(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Return all request list");
        return itemRequestClient.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long requestId
    ) {
        log.info("Return request by id");
        return itemRequestClient.getRequestById(userId, requestId);
    }
}
