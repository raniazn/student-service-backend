package org.example.academicservice.controller;

import org.example.academicservice.dto.MatiereDTO;
import org.example.academicservice.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @GetMapping
    public ResponseEntity<List<MatiereDTO>> getAll() {
        return ResponseEntity.ok(matiereService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MatiereDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(matiereService.search(nom));
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<MatiereDTO>> getByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(matiereService.getByModule(moduleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatiereDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(matiereService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MatiereDTO> create(@RequestBody MatiereDTO dto) {
        return ResponseEntity.ok(matiereService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatiereDTO> update(@PathVariable Long id, @RequestBody MatiereDTO dto) {
        return ResponseEntity.ok(matiereService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matiereService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{matiereId}/affecter-module/{moduleId}")
    public ResponseEntity<MatiereDTO> affecterModule(
            @PathVariable Long matiereId,
            @PathVariable Long moduleId) {
        return ResponseEntity.ok(matiereService.affecterModule(matiereId, moduleId));
    }
}