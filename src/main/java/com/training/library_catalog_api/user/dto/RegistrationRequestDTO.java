package com.training.library_catalog_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public record RegistrationRequestDTO (
    @NotBlank(message="Name cannot be empty")
    @Size(max=100 , message="Name cannot exceed maximum length(100)")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Name can only contain letters, spaces, dots, apostrophes and hyphens")
    String name,

    @NotBlank(message="Email cannot be empty")
    @Size(max=254 , message="Email cannot exceed maximum length")
    @Email(message="Invalid email format")
    String email,

    @NotBlank(message="Password cannot be empty")
    @Size(min=10,message="Password must contain at least 10 characters")
    String password

){}
