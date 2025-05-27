package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.client.fallback.EventClientFallback;

@FeignClient(name = "event-service", fallback = EventClientFallback.class)
public interface EventClient {


}
