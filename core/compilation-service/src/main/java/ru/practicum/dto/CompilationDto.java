package ru.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private List<Long> eventIds;
    private boolean pinned = false;
    private String title;
}
