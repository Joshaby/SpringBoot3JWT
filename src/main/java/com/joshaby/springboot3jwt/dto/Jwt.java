package com.joshaby.springboot3jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Jwt {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String username;

    private String email;

    public Jwt(String token, Long id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }

}
