package org.example.academicservice.serviceImpl;
import org.example.academicservice.dto.SalleDTO;           // ← bon package
import org.example.academicservice.entity.Salle;           // ← bon package
import org.example.academicservice.repository.SalleRepository; // ← bon package
import org.example.academicservice.service.SalleService;   // ← bon package
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements SalleService {


    private final SalleRepository salleRepository;

    @Override
    public List<SalleDTO> getAll() {
        return salleRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SalleDTO> search(String nom) {
        return salleRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SalleDTO getById(Long id) {
        return toDTO(salleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salle non trouvée : " + id)));
    }

    @Override
    public SalleDTO create(SalleDTO dto) {
        Salle salle = Salle.builder()
                .nom(dto.getNom())
                .capacite(dto.getCapacite())
                .build();
        return toDTO(salleRepository.save(salle));
    }

    @Override
    public SalleDTO update(Long id, SalleDTO dto) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salle non trouvée : " + id));
        salle.setNom(dto.getNom());
        salle.setCapacite(dto.getCapacite());
        return toDTO(salleRepository.save(salle));
    }

    @Override
    public void delete(Long id) {
        if (!salleRepository.existsById(id))
            throw new EntityNotFoundException("Salle non trouvée : " + id);
        salleRepository.deleteById(id);
    }

    // ── Mapper ──
    private SalleDTO toDTO(Salle s) {
        return SalleDTO.builder()
                .id(s.getId())
                .nom(s.getNom())
                .capacite(s.getCapacite())
                .build();
    }
}

