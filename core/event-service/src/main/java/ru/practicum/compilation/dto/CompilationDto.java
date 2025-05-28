package ru.practicum.compilation.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private List<Long> events;
    private boolean pinned = false;
    private String title;
}
