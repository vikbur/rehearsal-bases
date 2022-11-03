package org.vikbur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vikbur.models.requests.AuthRequest;
import org.vikbur.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class AuthController {
    private final AuthService authService;
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Новые accessToken и refreshToken"),
                    @ApiResponse(responseCode = "401", description = "Неправильный логин или пароль")
            }
    )
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}
