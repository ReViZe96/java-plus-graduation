package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.AdminUpdateCommentStatusDto;
import ru.practicum.dto.comments.CommentDto;
import ru.practicum.enums.CommentStatus;
import ru.practicum.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "admin/comments")
public class CommentAdminController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> adminPendigCommentList() {
        return commentService.adminPendigCommentList();
    }

    @PatchMapping("/{commentId}")
    public CommentDto adminUpdateCommentStatus(@PathVariable("commentId") Long commentId,
                                               @Valid @RequestBody AdminUpdateCommentStatusDto dto) {
        return commentService.adminUpdateCommentStatus(commentId, dto);
    }

    @GetMapping("/byEventId/{eventId}/andCommentStatus")
    public List<CommentDto> findByEventIdAndStatus(@PathVariable Long eventId, @RequestParam CommentStatus commentStatus) {
        return commentService.findByEventIdAndStatus(eventId, commentStatus);
    }

    @GetMapping("/all/byEventIdsAndCommentStatus")
    List<CommentDto> findAllByEventIdInAndStatus(@RequestParam List<Long> idsList, @RequestParam CommentStatus commentStatus) {
        return commentService.findAllByEventIdInAndStatus(idsList, commentStatus);
    }

}
