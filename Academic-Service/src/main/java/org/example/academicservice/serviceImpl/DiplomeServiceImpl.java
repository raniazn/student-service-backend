package org.example.academicservice.serviceImpl;

// ── Corriger tous les imports ──
import org.example.academicservice.dto.DiplomeDTO;
import org.example.academicservice.entity.Cycle;
import org.example.academicservice.entity.Diplome;
import org.example.academicservice.repository.CycleRepository;
import org.example.academicservice.repository.DiplomeRepository;
import org.example.academicservice.service.DiplomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiplomeServiceImpl implements DiplomeService {

    private final DiplomeRepository diplomeRepository;
    private final CycleRepository cycleRepository;

    @Override
    public List<DiplomeDTO> getAll() {
        return diplomeRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<DiplomeDTO> search(String nom) {
        return diplomeRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DiplomeDTO getById(Long id) {
        return toDTO(diplomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Diplôme non trouvé : " + id)));
    }

    @Override
    public DiplomeDTO create(DiplomeDTO dto) {
        Cycle cycle = cycleRepository.findById(dto.getCycleId())
                .orElseThrow(() -> new EntityNotFoundException("Cycle non trouvé : " + dto.getCycleId()));
        Diplome diplome = Diplome.builder()
                .nom(dto.getNom())
                .nature(dto.getNature())
                .cycle(cycle)
                .build();
        return toDTO(diplomeRepository.save(diplome));
    }

    @Override
    public DiplomeDTO update(Long id, DiplomeDTO dto) {
        Diplome diplome = diplomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Diplôme non trouvé : " + id));
        Cycle cycle = cycleRepository.findById(dto.getCycleId())
                .orElseThrow(() -> new EntityNotFoundException("Cycle non trouvé : " + dto.getCycleId()));
        diplome.setNom(dto.getNom());
        diplome.setNature(dto.getNature());
        diplome.setCycle(cycle);
        return toDTO(diplomeRepository.save(diplome));
    }

    @Override
    public void delete(Long id) {
        if (!diplomeRepository.existsById(id))
            throw new EntityNotFoundException("Diplôme non trouvé : " + id);
        diplomeRepository.deleteById(id);
    }

    // ── Mapper ──
    private DiplomeDTO toDTO(Diplome d) {
        return DiplomeDTO.builder()
                .id(d.getId())
                .nom(d.getNom())
                .nature(d.getNature())
                .cycleId(d.getCycle().getId())
                .cycleNom(d.getCycle().getNom())
                .build();
    }
}                       // ← une seule accolade fermante ici, supprimer l'extra '}'