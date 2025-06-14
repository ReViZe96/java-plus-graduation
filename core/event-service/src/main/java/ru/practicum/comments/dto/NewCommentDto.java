package ru.practicum.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentDto {
    @NotBlank
    @Size(max = 1000)
    private String text;
}
