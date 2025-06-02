package ru.practicum.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.client.EventClient;
import ru.practicum.dto.events.EventDto;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class EventClientFallback implements EventClient {

    private static final String SERVICE_UNAVAILABLE = "Сервис 'События' временно недоступен: ";


    @Override
    public List<EventDto> getEvents(List<Long> ids) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить события по id.");
        return Collections.emptyList();
    }

    @Override
    public List<EventDto> findAllByCategoryId(Long categoryId) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить события, относящиеся к категории с id = {}.", categoryId);
        return Collections.emptyList();
    }

    @Override
    public EventDto findByIdAndInitiatorId(Long eventId, Long userId) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить событие с id = {}, созданное пользователем с id = {}.",
                eventId, userId);
        return null;
    }

    @Override
    public List<EventDto> findAllByInitiatorId(Long initiatorId) {
        log.warn(SERVICE_UNAVAILABLE + "невозможно получить события, созданные пользователем с id = {}.", initiatorId);
        return Collections.emptyList();
    }

}
