package org.example.academicservice.serviceImpl;
import org.example.academicservice.dto.SpecialiteDTO;           // ← bon package
import org.example.academicservice.entity.Diplome;              // ← bon package
import org.example.academicservice.entity.Module;               // ← bon package
import org.example.academicservice.entity.Specialite;           // ← bon package
import org.example.academicservice.repository.DiplomeRepository;   // ← bon package
import org.example.academicservice.repository.ModuleRepository;    // ← bon package
import org.example.academicservice.repository.SpecialiteRepository;// ← bon package
import org.example.academicservice.service.SpecialiteService;   // ← bon package
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialiteServiceImpl implements SpecialiteService {

    private final SpecialiteRepository specialiteRepository;
    private final DiplomeRepository diplomeRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public List<SpecialiteDTO> getAll() {
        return specialiteRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SpecialiteDTO> search(String nom) {
        return specialiteRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SpecialiteDTO getById(Long id) {
        return toDTO(specialiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée : " + id)));
    }

    @Override
    public SpecialiteDTO create(SpecialiteDTO dto) {
        Diplome diplome = diplomeRepository.findById(dto.getDiplomeId())
                .orElseThrow(() -> new EntityNotFoundException("Diplôme non trouvé : " + dto.getDiplomeId()));
        Specialite specialite = Specialite.builder()
                .nom(dto.getNom())
                .niveauSemestre(dto.getNiveauSemestre())
                .diplome(diplome)
                .build();
        return toDTO(specialiteRepository.save(specialite));
    }

    @Override
    public SpecialiteDTO update(Long id, SpecialiteDTO dto) {
        Specialite specialite = specialiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée : " + id));
        Diplome diplome = diplomeRepository.findById(dto.getDiplomeId())
                .orElseThrow(() -> new EntityNotFoundException("Diplôme non trouvé : " + dto.getDiplomeId()));
        specialite.setNom(dto.getNom());
        specialite.setNiveauSemestre(dto.getNiveauSemestre());
        specialite.setDiplome(diplome);
        return toDTO(specialiteRepository.save(specialite));
    }

    @Override
    public void delete(Long id) {
        if (!specialiteRepository.existsById(id))
            throw new EntityNotFoundException("Spécialité non trouvée : " + id);
        specialiteRepository.deleteById(id);
    }

    @Override
    public SpecialiteDTO affecterModules(Long specialiteId, List<Long> moduleIds) {
        Specialite specialite = specialiteRepository.findById(specialiteId)
                .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée : " + specialiteId));
        List<Module> modules = moduleRepository.findAllById(moduleIds);
        specialite.setModules(modules);
        return toDTO(specialiteRepository.save(specialite));
    }

    // ── Mapper ──
    private SpecialiteDTO toDTO(Specialite s) {
        List<Long> moduleIds = (s.getModules() == null) ? List.of() :
                s.getModules().stream().map(Module::getId).collect(Collectors.toList());
        return SpecialiteDTO.builder()
                .id(s.getId())
                .nom(s.getNom())
                .niveauSemestre(s.getNiveauSemestre())
                .diplomeId(s.getDiplome().getId())
                .diplomeNom(s.getDiplome().getNom())
                .cycleNom(s.getDiplome().getCycle().getNom())
                .moduleIds(moduleIds)
                .build();
    }
}
