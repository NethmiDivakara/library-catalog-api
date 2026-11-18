package com.training.library_catalog_api.user.controller;

import com.training.library_catalog_api.user.dto.LoginRequestDTO;
import com.training.library_catalog_api.user.dto.RegistrationRequestDTO;
import com.training.library_catalog_api.user.dto.TokenResponseDTO;
import com.training.library_catalog_api.user.dto.UserResponseDTO;
import com.training.library_catalog_api.user.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

//    @GetMapping("/csrf-token")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CsrfToken getCsrfToken(HttpServletRequest request){
//       return (CsrfToken)request.getAttribute("_csrf");
//    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO register(
            @Valid @RequestBody RegistrationRequestDTO registerRequest
    ) {
        System.out.println("Registration reached");
        return userService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDTO login(
            @Valid @RequestBody LoginRequestDTO loginRequest
    ){
        System.out.println("Login reached");
        return userService.login(loginRequest);
    }

// @PostMapping("/login")
// @ResponseStatus(HttpStatus.CREATED)
//    public UserResponseDTO login(
//            @RequestBody
//            return
//
// )


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO me(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null || jwt.getSubject() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        long userId;
        try {
            userId = Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        return userService.authMe(userId);
    }
}
