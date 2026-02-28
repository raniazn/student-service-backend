package org.example.teachingservice.controller;

import org.example.teachingservice.dto.EmploiFormationSoirDTO;
import org.example.teachingservice.service.EmploiFormationSoirService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emploi-formation-soir")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmploiFormationSoirController {

    private final EmploiFormationSoirService service;

    @GetMapping
    public ResponseEntity<List<EmploiFormationSoirDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmploiFormationSoirDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(service.search(query));
    }

    @PostMapping
    public ResponseEntity<EmploiFormationSoirDTO> create(@RequestBody EmploiFormationSoirDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploiFormationSoirDTO> update(
            @PathVariable Long id, @RequestBody EmploiFormationSoirDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
