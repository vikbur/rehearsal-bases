package org.vikbur.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vikbur.models.requests.AuthRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class AuthController {

    /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {

        } catch (Exception e){

        }
    }*/
}
