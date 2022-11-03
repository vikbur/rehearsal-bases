package org.vikbur.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bases")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class BasesController {
    @GetMapping
    public ResponseEntity<String> HelloWorld() {
        return ResponseEntity.ok("Hello world!");
    }
}
