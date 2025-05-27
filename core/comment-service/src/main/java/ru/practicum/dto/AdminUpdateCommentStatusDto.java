package ru.practicum.dto;

import lombok.Data;
import ru.practicum.enums.AdminUpdateCommentStatusAction;

@Data
public class AdminUpdateCommentStatusDto {
    private AdminUpdateCommentStatusAction action;
}
