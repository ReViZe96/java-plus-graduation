package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.client.fallback.CommentClientFallback;
import ru.practicum.dto.comments.CommentDto;
import ru.practicum.dto.enums.CommentStatus;

import java.util.List;

@FeignClient(name = "comment-service", fallback = CommentClientFallback.class)
public interface CommentClient {

    /**
     * Получение комментариев по конкретному событию в конкретном статусе.
     * @param eventId идентификатор события
     * @param commentStatus статус комментариев
     * @return список комментариев.
     */
    @GetMapping("/users/{userId}/comments/byEventId/{eventId}/andCommentStatus/{commentStatus}")
    List<CommentDto> findByEventIdAndStatus(@PathVariable Long eventId, @PathVariable CommentStatus commentStatus);

    /**
     * Получение комментариев с конкретным статусом по конкретному списку событий.
     * @param idsList список идентификаторов событий
     * @param commentStatus статус комментариев
     * @return список комментариев.
     */
    @GetMapping("/users/{userId}/comments/all//byEventIdsAndCommentStatus/{commentStatus}")
    List<CommentDto> findAllByEventIdInAndStatus(@RequestParam List<Long> idsList, @PathVariable CommentStatus commentStatus);

}
