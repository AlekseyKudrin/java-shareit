package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.model.CommentDto;
import ru.practicum.shareit.comment.service.CommentService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                 @PathVariable(value = "itemId") long itemId,
                                 @RequestBody @Valid CommentDto commentDto
    ) {
        return commentService.addComment(userId, itemId, commentDto);
    }
}
