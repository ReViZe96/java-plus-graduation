package ru.practicum.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.client.CommentClient;
import ru.practicum.dto.comments.CommentDto;
import ru.practicum.dto.enums.CommentStatus;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class CommentClientFallback implements CommentClient {

    private static final String SERVICE_UNAVAILABLE = "Сервис 'Комментарии' временно недоступен: ";


    @Override
    public List<CommentDto> findByEventIdAndStatus(Long eventId, CommentStatus commentStatus) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить комментарии со статусом {} для события с id = {} .",
                commentStatus, eventId);
        return Collections.emptyList();
    }

    @Override
    public List<CommentDto> findAllByEventIdInAndStatus(List<Long> idsList, CommentStatus commentStatus) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить комментарии со статусом {} для указанных событий.",
                commentStatus);
        return Collections.emptyList();
    }

}
