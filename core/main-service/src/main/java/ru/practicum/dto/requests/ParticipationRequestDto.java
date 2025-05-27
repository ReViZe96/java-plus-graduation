package ru.practicum.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParticipationRequestDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long eventId;

    private Long requesterId;

    private String created;

    private String status;

}
