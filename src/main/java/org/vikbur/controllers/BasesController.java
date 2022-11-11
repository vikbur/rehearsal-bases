package org.vikbur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vikbur.models.requests.CreateBaseRequest;
import org.vikbur.services.AuthService;
import org.vikbur.services.BaseService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/bases")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class BasesController {
    private final BaseService baseService;
    private final AuthService authService;
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавление новой музыльканой группы",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Группа успешно добавлена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса")
            }
    )
    public ResponseEntity<String> createBand(@RequestBody CreateBaseRequest createBaseRequest){
        String userLogin = authService.getAuthInfo().getLogin();
        try {
            return ResponseEntity.ok(baseService.createBase(createBaseRequest, userLogin));
        } catch (Exception e){
            log.error("createBand error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение реп. базы по идентификтору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные по реп. базе"),
                    @ApiResponse(responseCode = "400", description = "Некорректный идентификтор базы"),
                    @ApiResponse(responseCode = "404", description = "База с указанным идентификатором не найдена")
            }
    )
    public ResponseEntity<String> getBaseById(@PathVariable int id){
        String userLogin = authService.getAuthInfo().getLogin();
        try {
            return ResponseEntity.ok(baseService.getBaseById(id, userLogin));
        } catch (IllegalArgumentException e){
            log.error("createBand error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            log.error("createBand error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
