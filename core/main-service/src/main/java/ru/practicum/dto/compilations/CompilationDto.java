package ru.practicum.dto.compilations;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private boolean pinned = false;
    private String title;
}
