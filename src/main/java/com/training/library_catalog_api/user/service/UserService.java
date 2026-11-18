package com.training.library_catalog_api.user.service;


import com.training.library_catalog_api.user.dto.LoginRequestDTO;
import com.training.library_catalog_api.user.dto.RegistrationRequestDTO;
import com.training.library_catalog_api.user.dto.TokenResponseDTO;
import com.training.library_catalog_api.user.dto.UserResponseDTO;

public interface UserService {

UserResponseDTO register(RegistrationRequestDTO RegisterRequest);

TokenResponseDTO login (LoginRequestDTO loginRequest);

UserResponseDTO authMe(Long userId);

}
