package org.example.academicservice.controller;

import org.example.academicservice.dto.CycleDTO;
import org.example.academicservice.entity.Cycle;
import org.example.academicservice.repository.CycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cycles")
@RequiredArgsConstructor
public class CycleController {

    private final CycleRepository cycleRepository;

    @GetMapping
    public ResponseEntity<List<CycleDTO>> getAll() {
        List<CycleDTO> list = cycleRepository.findAll().stream()
                .map(c -> new CycleDTO(c.getId(), c.getNom()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CycleDTO> create(@RequestBody CycleDTO dto) {
        Cycle saved = cycleRepository.save(
                Cycle.builder().nom(dto.getNom()).build()
        );
        return ResponseEntity.ok(
                new CycleDTO(saved.getId(), saved.getNom())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cycleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}