package ru.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.enums.CommentStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "author_id")
    private Long authorId;

    private LocalDateTime created = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private CommentStatus status = CommentStatus.PENDING;
}
