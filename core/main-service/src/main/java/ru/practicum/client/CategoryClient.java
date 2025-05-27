package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.client.fallback.CategoryClientFallback;

@FeignClient(name = "category-service", fallback = CategoryClientFallback.class)
public interface CategoryClient {


}
