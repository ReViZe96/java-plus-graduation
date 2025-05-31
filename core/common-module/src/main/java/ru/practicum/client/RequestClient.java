package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.client.fallback.RequestClientFallback;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.requests.ParticipationRequestDto;

import java.util.List;


@FeignClient(name = "request-service", fallback = RequestClientFallback.class)
public interface RequestClient {

    /**
     * Получение запроса на участие в конкретном событии с конкретным статусом от конкретного пользователя.
     * @param userId идентификатор пользователя
     * @param eventId идентификатор события
     * @param requestStatus требуемый статус события
     * @return запрос на участие в событии.
     */
    @GetMapping("/users/{userId}/events/{eventId}/requests/byStatus")
    ParticipationRequestDto findByRequesterIdAndEventIdAndStatus(@PathVariable Long userId,
                                                                 @PathVariable Long eventId,
                                                                 @RequestParam RequestStatus requestStatus);

    /**
     * Получить количество запросов на участие в конкретном событии с конкретным статусом.
     * @param userId идентификатор пользователя
     * @param eventId идентификатор события
     * @param requestStatus требуемый статус события
     * @return количество запросов на участие в событии.
     */
    @GetMapping("/users/{userId}/events/{eventId}/requestsCount")
    Long countRequestsByEventIdAndStatus(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @RequestParam RequestStatus requestStatus);

    /**
     * Получить все запросы на участие в конкретных событиях с конкретным статусом.
     * @param idsList список идентификаторов событий
     * @param userId идентификатор пользователя
     * @param requestStatus требуемый статус событий
     * @return список запросов на участие в событиях.
     */
    @GetMapping("/users/{userId}/events/requests")
    List<ParticipationRequestDto> findAllByEventIdInAndStatus(@RequestParam List<Long> idsList,
                                                              @PathVariable Long userId,
                                                              @RequestParam RequestStatus requestStatus);

}
