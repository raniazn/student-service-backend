package org.example.academicservice.controller;

import org.example.academicservice.dto.ModuleDTO;
import org.example.academicservice.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getAll() {
        return ResponseEntity.ok(moduleService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ModuleDTO>> search(@RequestParam String nom) {
        return ResponseEntity.ok(moduleService.search(nom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ModuleDTO> create(@RequestBody ModuleDTO dto) {
        return ResponseEntity.ok(moduleService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> update(@PathVariable Long id, @RequestBody ModuleDTO dto) {
        return ResponseEntity.ok(moduleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}