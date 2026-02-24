package org.example.academicservice.controller;

import org.example.academicservice.dto.DiplomeDTO;        // ← bon package
import org.example.academicservice.service.DiplomeService; // ← bon package
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diplomes")
@RequiredArgsConstructor
public class DiplomeController {

    private final DiplomeService diplomeService;

    @GetMapping
    public ResponseEntity<List<DiplomeDTO>> getAll() {
        return ResponseEntity.ok(diplomeService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiplomeDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(diplomeService.search(nom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiplomeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(diplomeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DiplomeDTO> create(@RequestBody DiplomeDTO dto) {
        return ResponseEntity.ok(diplomeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiplomeDTO> update(@PathVariable Long id, @RequestBody DiplomeDTO dto) {
        return ResponseEntity.ok(diplomeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        diplomeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}