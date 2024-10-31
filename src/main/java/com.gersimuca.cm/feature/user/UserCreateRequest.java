package com.gersimuca.cm.feature.user;

import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty(message = "Username cannot be empty or null")
        String username,
        @NotEmpty(message = "Password cannot be empty or null")
        String password)
{}
