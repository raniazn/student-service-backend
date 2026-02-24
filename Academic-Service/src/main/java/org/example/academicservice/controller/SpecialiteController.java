package org.example.academicservice.controller;

import org.example.academicservice.dto.SpecialiteDTO;
import org.example.academicservice.service.SpecialiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@RequiredArgsConstructor
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    @GetMapping
    public ResponseEntity<List<SpecialiteDTO>> getAll() {
        return ResponseEntity.ok(specialiteService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<SpecialiteDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(specialiteService.search(nom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialiteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(specialiteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SpecialiteDTO> create(@RequestBody SpecialiteDTO dto) {
        return ResponseEntity.ok(specialiteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialiteDTO> update(@PathVariable Long id, @RequestBody SpecialiteDTO dto) {
        return ResponseEntity.ok(specialiteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialiteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/affecter-modules")
    public ResponseEntity<SpecialiteDTO> affecterModules(
            @PathVariable Long id,
            @RequestBody List<Long> moduleIds) {
        return ResponseEntity.ok(specialiteService.affecterModules(id, moduleIds));
    }
}