package com.training.library_catalog_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public record LoginRequestDTO (

    @NotBlank(message="Email cannot be empty")
    @Size(max=254 , message="Email cannot exceed maximum length")
    @Email(message="Invalid email format")
    String email,

    @NotBlank(message="Password cannot be empty")
    String password
){}
