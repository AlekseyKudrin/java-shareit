package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class CommentController {

    private final CommentClient commentClient;

    @PostMapping(path = "/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                             @PathVariable(value = "itemId") long itemId,
                                             @RequestBody @Valid CommentDto commentDto
    ) {
        return commentClient.addComment(userId, itemId, commentDto);
    }
}
