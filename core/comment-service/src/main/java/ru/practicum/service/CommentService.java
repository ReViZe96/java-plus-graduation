package ru.practicum.service;

import ru.practicum.dto.AdminUpdateCommentStatusDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.dto.comments.CommentDto;
import ru.practicum.enums.CommentStatus;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long userId, long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(long userId, long commentId, NewCommentDto updateCommentDto);

    void deleteComment(long userId, long commentId);

    CommentDto adminUpdateCommentStatus(Long commentId, AdminUpdateCommentStatusDto dto);

    List<CommentDto> adminPendigCommentList();

    List<CommentDto> findByEventIdAndStatus(Long eventId, CommentStatus commentStatus);

    List<CommentDto> findAllByEventIdInAndStatus(List<Long> idsList, CommentStatus commentStatus);
}
