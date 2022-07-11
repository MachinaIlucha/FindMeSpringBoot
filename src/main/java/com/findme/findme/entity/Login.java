package com.findme.findme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Login {
    private final String email;
    private final String password;
}
