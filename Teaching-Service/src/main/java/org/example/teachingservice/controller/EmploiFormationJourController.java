package org.example.teachingservice.controller;
import org.example.teachingservice.dto.EmploiFormationJourDTO;
import org.example.teachingservice.service.EmploiFormationJourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emploi-formation-jour")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmploiFormationJourController {

    private final EmploiFormationJourService service;

    @GetMapping
    public ResponseEntity<List<EmploiFormationJourDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmploiFormationJourDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(service.search(query));
    }

    @PostMapping
    public ResponseEntity<EmploiFormationJourDTO> create(@RequestBody EmploiFormationJourDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploiFormationJourDTO> update(
            @PathVariable Long id,
            @RequestBody EmploiFormationJourDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
