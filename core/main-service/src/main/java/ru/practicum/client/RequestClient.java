package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.client.fallback.RequestClientFallback;

@FeignClient(name = "request-service", fallback = RequestClientFallback.class)
public interface RequestClient {


}
