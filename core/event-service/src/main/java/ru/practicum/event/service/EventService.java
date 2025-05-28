package ru.practicum.event.service;

import ru.practicum.event.dto.EntityParam;
import ru.practicum.event.dto.EventAdminUpdateDto;
import ru.practicum.event.dto.EventCreateDto;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.EventUpdateDto;
import ru.practicum.event.dto.SearchEventsParam;

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
