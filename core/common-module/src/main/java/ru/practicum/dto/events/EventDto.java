package ru.practicum.dto.events;

import lombok.Data;
import ru.practicum.dto.categories.CategoryDto;
import ru.practicum.dto.comments.CommentDto;
import ru.practicum.dto.users.UserShortDto;

import java.util.List;

@Data
public class EventDto {
    private Long id;
    private String title;
    private String annotation;
    private String description;

    private long confirmedRequests;
    private long views;

    private boolean paid;
    private boolean requestModeration;
    private int participantLimit;

    private CategoryDto category;
    private LocationDto location;

    private String eventState;
    private UserShortDto initiator;

    private String eventDate;
    private String createdOn;
    private String publishedOn;

    private List<CommentDto> comments;
}
