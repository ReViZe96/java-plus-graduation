package ru.practicum.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.EntityParam;
import ru.practicum.dto.EventAdminUpdateDto;
import ru.practicum.dto.EventCreateDto;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.EventUpdateDto;
import ru.practicum.dto.SearchEventsParam;

import java.util.List;

public interface EventService {

    List<EventDto> adminEventsSearch(SearchEventsParam searchEventsParam);

    EventDto adminEventUpdate(Long eventId, EventAdminUpdateDto eventDto);

    List<EventDto> privateUserEvents(Long userId, int from, int size);

    EventDto privateEventCreate(Long userId, EventCreateDto eventCreateDto);

    EventDto privateGetUserEvent(Long userId, Long eventId);

    EventDto privateUpdateUserEvent(Long userId, Long eventId, EventUpdateDto eventUpdateDto);

    List<EventShortDto> getEvents(EntityParam params);

    EventDto getEvent(Long eventId);

    List<EventDto> getEvents(List<Long> ids);

    List<EventDto> findAllByCategoryId(Long categoryId);

    EventDto findByIdAndInitiatorId(Long eventId, Long userId);

    List<EventDto> findAllByInitiatorId(Long initiatorId);

}
