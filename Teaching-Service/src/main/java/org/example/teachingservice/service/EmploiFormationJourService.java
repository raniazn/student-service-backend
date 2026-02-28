package org.example.teachingservice.service;

import org.example.teachingservice.dto.EmploiFormationJourDTO;
import org.example.teachingservice.entity.EmploiFormationJour;
import org.example.teachingservice.repository.EmploiFormationJourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploiFormationJourService {

    private final EmploiFormationJourRepository repository;

    public List<EmploiFormationJourDTO> getAll() {
        return repository.findByTypeFormation("JOUR")
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EmploiFormationJourDTO> search(String query) {
        return repository
                .findByEnseignantNomContainingIgnoreCaseOrMatiereContainingIgnoreCaseOrGroupeContainingIgnoreCase(
                        query, query, query
                )
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EmploiFormationJourDTO create(EmploiFormationJourDTO dto) {
        EmploiFormationJour entity = toEntity(dto);
        entity.setTypeFormation("JOUR");
        return toDTO(repository.save(entity));
    }

    public EmploiFormationJourDTO update(Long id, EmploiFormationJourDTO dto) {
        EmploiFormationJour existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emploi non trouvé"));
        existing.setEnseignantNom(dto.getEnseignantNom());
        existing.setMatiere(dto.getMatiere());
        existing.setGroupe(dto.getGroupe());
        existing.setSemestre(dto.getSemestre());
        existing.setSalle(dto.getSalle());
        return toDTO(repository.save(existing));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Mappers
    private EmploiFormationJourDTO toDTO(EmploiFormationJour e) {
        return EmploiFormationJourDTO.builder()
                .id(e.getId())
                .enseignantId(e.getEnseignantId())
                .enseignantNom(e.getEnseignantNom())
                .matiere(e.getMatiere())
                .groupe(e.getGroupe())
                .semestre(e.getSemestre())
                .salle(e.getSalle())
                .typeFormation(e.getTypeFormation())
                .build();
    }

    private EmploiFormationJour toEntity(EmploiFormationJourDTO dto) {
        return EmploiFormationJour.builder()
                .enseignantId(dto.getEnseignantId())
                .enseignantNom(dto.getEnseignantNom())
                .matiere(dto.getMatiere())
                .groupe(dto.getGroupe())
                .semestre(dto.getSemestre())
                .salle(dto.getSalle())
                .build();
    }
}
