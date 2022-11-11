package org.vikbur.controllers;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vikbur.models.requests.CreateRoomRequest;
import org.vikbur.services.AuthService;
import org.vikbur.services.RoomService;

@RestController
@RequestMapping("api/rooms")
@RequiredArgsConstructor
@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class RoomController {
    private final AuthService authService;
    private final RoomService roomService;
    private final Gson gson = new Gson();

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавление нового зала в реп. базе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Зал успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса")
            }
    )
    public ResponseEntity<String> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        String userLogin = authService.getAuthInfo().getLogin();
        try {
            return ResponseEntity.ok(roomService.createRoom(createRoomRequest, userLogin));
        } catch (Exception e){
            log.error("createRoom error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }
}
