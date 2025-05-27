package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.EventClient;
import ru.practicum.client.RequestClient;
import ru.practicum.client.UserClient;
import ru.practicum.dto.AdminUpdateCommentStatusDto;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.dto.events.EventDto;
import ru.practicum.dto.users.UserDto;
import ru.practicum.enums.AdminUpdateCommentStatusAction;
import ru.practicum.enums.CommentStatus;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.OperationForbiddenException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final EventClient eventClient;
    private final CommentMapper commentMapper;
    private final RequestClient requestClient;

    @Transactional
    @Override
    public CommentDto createComment(long authorId, long eventId, NewCommentDto newCommentDto) {
        List<UserDto> author = userClient.getUsers(List.of(authorId), 0, 1);
        if (author.isEmpty()) {
            throw new NotFoundException(String.format("User with ID %s not found", authorId));
        }

        List<EventDto> event = eventClient.getEvents(List.of(eventId));
        if (event.isEmpty()) {
            throw new NotFoundException(String.format("Event with ID %s not found", eventId));
        }

        if (authorId == event.get(0).getInitiator().getId()) {
            throw new OperationForbiddenException("Инициатор мероприятия не может оставлять комментарии к нему");
        }
        if (!event.get(0).getEventState().equals("PUBLISHED")) {
            throw new OperationForbiddenException("Мероприятие должно быть опубликовано");
        }
        if (requestClient.findByRequesterIdAndEventIdAndStatus(authorId, eventId, "CONFIRMED") == null) {
            throw new OperationForbiddenException("Комментарии может оставлять только подтвержденный участник мероприятия");
        }
        Comment comment = commentMapper.toComment(newCommentDto, author.getFirst().getId(), event.get(0).getId());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Transactional
    @Override
    public CommentDto updateComment(long authorId, long commentId, NewCommentDto updateCommentDto) {
        Comment commentToUpdate = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with ID %s not found", commentId)));
        if (authorId != commentToUpdate.getAuthorId()) {
            throw new OperationForbiddenException("Изменить комментарий может только его автор");
        }
        commentToUpdate.setText(updateCommentDto.getText());
        commentToUpdate.setStatus(CommentStatus.PENDING);

        commentRepository.save(commentToUpdate);
        return commentMapper.toDto(commentToUpdate);
    }

    @Transactional
    @Override
    public void deleteComment(long authorId, long commentId) {
        Comment commentToDelete = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with ID %s not found", commentId)));
        if (authorId != commentToDelete.getAuthorId()) {
            throw new OperationForbiddenException("Удалить комментарий может только его автор");
        }
        commentRepository.delete(commentToDelete);
    }

    @Transactional
    @Override
    public CommentDto adminUpdateCommentStatus(Long commentId, AdminUpdateCommentStatusDto updateCommentStatusDto) {
        Comment commentToUpdateStatus = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with ID %s not found", commentId)));
        if (!commentToUpdateStatus.getStatus().equals(CommentStatus.PENDING)) {
            throw new OperationForbiddenException("Can't reject not pending comment");
        }
        if (updateCommentStatusDto.getAction().equals(AdminUpdateCommentStatusAction.PUBLISH_COMMENT)) {
            commentToUpdateStatus.setStatus(CommentStatus.PUBLISHED);
        }
        if (updateCommentStatusDto.getAction().equals(AdminUpdateCommentStatusAction.REJECT_COMMENT)) {
            commentToUpdateStatus.setStatus(CommentStatus.REJECTED);
        }
        commentRepository.save(commentToUpdateStatus);
        return commentMapper.toDto(commentToUpdateStatus);
    }

    @Override
    public List<CommentDto> adminPendigCommentList() {
        return commentRepository.findAllByStatus(CommentStatus.PENDING)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public List<CommentDto> findByEventIdAndStatus(Long eventId, String commentStatus) {
        return commentRepository.findByEventIdAndStatus(eventId, commentStatus).stream()
                .map(commentMapper::toDto).toList();
    }

    @Override
    public List<CommentDto> findAllByEventIdInAndStatus(List<Long> idsList, String commentStatus) {
        return commentRepository.findAllByEventIdInAndStatus(idsList, commentStatus).stream()
                .map(commentMapper::toDto).toList();
    }

}
