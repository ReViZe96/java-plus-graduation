package ru.practicum.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.client.RequestClient;
import ru.practicum.dto.requests.ParticipationRequestDto;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class RequestClientFallback implements RequestClient {

    private static final String SERVICE_UNAVAILABLE = "Сервис 'Запросы на участие' временно недоступен: ";


    @Override
    public ParticipationRequestDto findByRequesterIdAndEventIdAndStatus(Long userId, Long eventId, String requestStatus) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить запрос на участие в событии с id = {} от пользователя с id = {}.",
                eventId, userId);
        return null;
    }

    @Override
    public Long countRequestsByEventIdAndStatus(Long userId, Long eventId, String requestStatus) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить количество запросов на участие в событии с id = {}.",
                eventId);
        return null;
    }

    @Override
    public List<ParticipationRequestDto> findAllByEventIdInAndStatus(List<Long> idsList, Long userId, String requestStatus) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить запросы на участие в конкретных событиях со статусом {}.",
                requestStatus);
        return Collections.emptyList();
    }

}
