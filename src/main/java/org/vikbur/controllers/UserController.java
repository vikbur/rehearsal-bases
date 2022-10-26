package org.vikbur.controllers;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vikbur.models.User;
import org.vikbur.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final Gson gson = new Gson();

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Создание нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса")
            }
    )
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("");
        } catch (Exception e){
            log.error("Create user error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }
}
