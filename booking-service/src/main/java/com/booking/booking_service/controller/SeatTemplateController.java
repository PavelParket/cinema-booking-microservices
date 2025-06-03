package com.booking.booking_service.controller;

import com.booking.booking_service.dto.SeatTemplateRequest;
import com.booking.booking_service.dto.SeatTemplateResponse;
import com.booking.booking_service.service.SeatTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat-templates")
@RequiredArgsConstructor
public class SeatTemplateController {

    private final SeatTemplateService service;

    @PostMapping
    public ResponseEntity<SeatTemplateResponse> create(@RequestBody SeatTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatTemplateResponse> update(@PathVariable Long id, @RequestBody SeatTemplateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SeatTemplateResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatTemplateResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/scheme/{scheme}")
    public ResponseEntity<List<SeatTemplateResponse>> getByScheme(@PathVariable Integer scheme) {
        return ResponseEntity.ok(service.getByScheme(scheme));
    }
}
