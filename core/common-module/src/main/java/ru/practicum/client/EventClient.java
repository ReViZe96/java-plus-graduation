package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.client.fallback.EventClientFallback;
import ru.practicum.dto.events.EventDto;

import java.util.List;

@FeignClient(name = "event-service", fallback = EventClientFallback.class)
public interface EventClient {

    /**
     * Получение событий по их идентификаторам.
     *
     * @param ids список идентификаторов запрашиваемых событий
     * @return список событий.
     */
    @GetMapping("/events/byIds")
    List<EventDto> getEvents(@RequestParam(required = false) List<Long> ids);

    /**
     * Получение всех событий, относящихся к конкретной категории по идентификатору данной категории.
     *
     * @param categoryId идентификатор категории, по которой запрашиваются события
     * @return список событий конкретной категории.
     */
    @GetMapping("/events/byCategoryId/{categoryId}")
    List<EventDto> findAllByCategoryId(@PathVariable Long categoryId);

    /**
     * Получение события по его идентификатору и инициатору.
     *
     * @param eventId идентификатор события
     * @param userId  идентификатор инициатора события
     * @return конкретное событие.
     */
    @GetMapping("/events/byId/{eventId}/andInitiatorId/{userId}")
    EventDto findByIdAndInitiatorId(@PathVariable Long eventId, @PathVariable Long userId);

    /**
     * Получение всех событий, созданных конкретным пользователем.
     *
     * @param initiatorId идентификатор пользователя
     * @return список событий, созданных пользователем.
     */
    @GetMapping("/events/all/byInitiatorId/{initiatorId}")
    List<EventDto> findAllByInitiatorId(@PathVariable Long initiatorId);

}
