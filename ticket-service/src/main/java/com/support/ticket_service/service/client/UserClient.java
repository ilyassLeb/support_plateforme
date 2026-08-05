package com.support.ticket_service.service.client;




import com.support.ticket_service.config.FeignClientConfig;
import com.support.ticket_service.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "USER-SERVICE",
        path = "/api/users",
        configuration = FeignClientConfig.class
)
public interface UserClient {

    @GetMapping("/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id);
}