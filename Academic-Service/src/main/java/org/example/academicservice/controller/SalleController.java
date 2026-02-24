package org.example.academicservice.controller;

import org.example.academicservice.dto.SalleDTO;
import org.example.academicservice.service.SalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@RequiredArgsConstructor
public class SalleController {

    private final SalleService salleService;

    @GetMapping
    public ResponseEntity<List<SalleDTO>> getAll() {
        return ResponseEntity.ok(salleService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalleDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(salleService.search(nom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(salleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalleDTO> create(@RequestBody SalleDTO dto) {
        return ResponseEntity.ok(salleService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalleDTO> update(@PathVariable Long id, @RequestBody SalleDTO dto) {
        return ResponseEntity.ok(salleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}