package org.example.teachingservice.service;
import org.example.teachingservice.dto.EmploiFormationSoirDTO;
import org.example.teachingservice.entity.EmploiFormationSoir;
import org.example.teachingservice.repository.EmploiFormationSoirRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploiFormationSoirService {

    private final EmploiFormationSoirRepository repository;

    public List<EmploiFormationSoirDTO> getAll() {
        return repository.findByTypeFormation("SOIR")
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EmploiFormationSoirDTO> search(String query) {
        return repository
                .findByEnseignantNomContainingIgnoreCaseOrMatiereContainingIgnoreCaseOrGroupeContainingIgnoreCase(
                        query, query, query)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EmploiFormationSoirDTO create(EmploiFormationSoirDTO dto) {
        EmploiFormationSoir entity = toEntity(dto);
        entity.setTypeFormation("SOIR");
        return toDTO(repository.save(entity));
    }

    public EmploiFormationSoirDTO update(Long id, EmploiFormationSoirDTO dto) {
        EmploiFormationSoir existing = repository.findById(id)
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

    private EmploiFormationSoirDTO toDTO(EmploiFormationSoir e) {
        return EmploiFormationSoirDTO.builder()
                .id(e.getId()).enseignantId(e.getEnseignantId())
                .enseignantNom(e.getEnseignantNom()).matiere(e.getMatiere())
                .groupe(e.getGroupe()).semestre(e.getSemestre())
                .salle(e.getSalle()).typeFormation(e.getTypeFormation())
                .build();
    }

    private EmploiFormationSoir toEntity(EmploiFormationSoirDTO dto) {
        return EmploiFormationSoir.builder()
                .enseignantId(dto.getEnseignantId())
                .enseignantNom(dto.getEnseignantNom()).matiere(dto.getMatiere())
                .groupe(dto.getGroupe()).semestre(dto.getSemestre())
                .salle(dto.getSalle()).build();
    }
}