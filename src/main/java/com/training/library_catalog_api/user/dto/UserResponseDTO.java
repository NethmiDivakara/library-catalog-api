package com.training.library_catalog_api.user.dto;

import com.training.library_catalog_api.user.entity.Role;
import com.training.library_catalog_api.user.entity.User;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    Role role
    ){

    public static UserResponseDTO from (User user){

        return new UserResponseDTO(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getRole()
        );
    }
}



