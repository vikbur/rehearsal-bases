package org.vikbur.controllers;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vikbur.models.User;
import org.vikbur.models.requests.CreateUserRequest;
import org.vikbur.services.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class UserController {
    private final UserService userService;
    private final Gson gson = new Gson();

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Создание нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса")
            }
    )
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest userRequest) {
        try {
            return ResponseEntity.ok(userService.createUser(userRequest));
        } catch (Exception e){
            log.error("Create user error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение пользователя по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные пользователя"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    public ResponseEntity<String> getUserById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (IllegalArgumentException e){
            log.error("getUserById error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e.getMessage()));
        } catch (Exception e){
            log.error("getUserById error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    @GetMapping(value = "/by_login/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение пользователя по логину",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные пользователя"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    public ResponseEntity<String> getUserByLogin(@PathVariable String login) {
        try {
            return ResponseEntity.ok(userService.getUserByLogin(login));
        } catch (IllegalArgumentException e){
            log.error("getUserById error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e.getMessage()));
        } catch (Exception e){
            log.error("getUserById error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }
}
