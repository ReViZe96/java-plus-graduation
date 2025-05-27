package ru.practicum.service;

import ru.practicum.dto.AdminUpdateCommentStatusDto;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.enums.CommentStatus;
import ru.practicum.model.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long userId, long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(long userId, long commentId, NewCommentDto updateCommentDto);

    void deleteComment(long userId, long commentId);

    CommentDto adminUpdateCommentStatus(Long commentId, AdminUpdateCommentStatusDto dto);

    List<CommentDto> adminPendigCommentList();

    List<CommentDto> findByEventIdAndStatus(Long eventId, String commentStatus);

    List<CommentDto> findAllByEventIdInAndStatus(List<Long> idsList, String commentStatus);
}
