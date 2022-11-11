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
import org.vikbur.models.requests.CreateBandRequest;
import org.vikbur.services.AuthService;
import org.vikbur.services.BandService;

@RestController
@RequestMapping("api/bands")
@RequiredArgsConstructor
@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Неавторизованный запрос"),
        @ApiResponse(responseCode = "403", description = "Истек срок действия accessToken")
})
public class BandController {
    private final AuthService authService;
    private final BandService bandService;
    private final Gson gson = new Gson();

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавление новой музыльканой группы",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Группа успешно добавлена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса")
            }
    )
    public ResponseEntity<String> createBand(@RequestBody CreateBandRequest createBandRequest){
        String userLogin = authService.getAuthInfo().getLogin();
        try {
            return ResponseEntity.ok(bandService.createBand(createBandRequest, userLogin));
        } catch (Exception e){
            log.error("createBand error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение группы по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные группы"),
                    @ApiResponse(responseCode = "404", description = "Группа не найдена")
            }
    )
    public ResponseEntity<String> getBandById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bandService.getBandById(id));
        } catch (IllegalArgumentException e){
            log.error("getBandById error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e.getMessage()));
        } catch (Exception e){
            log.error("getBandById error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    @GetMapping(value = "/by_name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение группы по наименованию",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные группы"),
                    @ApiResponse(responseCode = "404", description = "Группа не найдена")
            }
    )
    public ResponseEntity<String> getBandByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(bandService.getBandByName(name));
        } catch (IllegalArgumentException e){
            log.error("getBandByName error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e.getMessage()));
        } catch (Exception e){
            log.error("getBandByName error: ", e);
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    //TODO: add member, change name
}
