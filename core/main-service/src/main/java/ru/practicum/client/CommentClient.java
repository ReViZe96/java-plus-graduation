package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.client.fallback.CommentClientFallback;

@FeignClient(name = "comment-service", fallback = CommentClientFallback.class)
public interface CommentClient {


}
