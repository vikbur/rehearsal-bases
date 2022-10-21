package org.vikbur.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bases")
public class BasesController {
    @GetMapping
    public ResponseEntity<String> HelloWorld() {
        return ResponseEntity.ok("Hello world!");
    }
}
