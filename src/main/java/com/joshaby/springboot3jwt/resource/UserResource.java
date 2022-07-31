package com.joshaby.springboot3jwt.resource;

import com.joshaby.springboot3jwt.dto.MessageDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class UserResource {

    @GetMapping("/greeting")
    @PreAuthorize("isAuthenticated()")
    public MessageDTO userAcess() {
        return new MessageDTO("Congratulations! You are an authenticated user.");
    }
}
