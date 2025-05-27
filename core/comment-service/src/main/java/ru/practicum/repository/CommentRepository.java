package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.enums.CommentStatus;
import ru.practicum.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByEventIdAndStatus(Long eventId, String commentStatus);

    List<Comment> findAllByEventIdInAndStatus(List<Long> idsList, String commentStatus);

    List<Comment> findAllByStatus(CommentStatus commentStatus);
}
