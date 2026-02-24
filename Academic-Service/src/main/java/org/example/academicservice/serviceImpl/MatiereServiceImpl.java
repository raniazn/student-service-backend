package org.example.academicservice.serviceImpl;
import org.example.academicservice.dto.MatiereDTO;        // ← bon package
import org.example.academicservice.entity.Matiere;        // ← bon package
import org.example.academicservice.entity.Module;         // ← bon package
import org.example.academicservice.repository.MatiereRepository; // ← bon package
import org.example.academicservice.repository.ModuleRepository;  // ← bon package
import org.example.academicservice.service.MatiereService;       // ← bon package
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatiereServiceImpl implements MatiereService {

    private final MatiereRepository matiereRepository;
    private final ModuleRepository moduleRepository;
    @Override
    public List<MatiereDTO> getAll() {
        return matiereRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MatiereDTO> search(String nom) {
        return matiereRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MatiereDTO> getByModule(Long moduleId) {
        return matiereRepository.findByModuleId(moduleId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public MatiereDTO getById(Long id) {
        return toDTO(matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée : " + id)));
    }

    @Override
    public MatiereDTO create(MatiereDTO dto) {
        Module module = null;
        if (dto.getModuleId() != null) {
            module = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module non trouvé : " + dto.getModuleId()));
        }
        Matiere matiere = Matiere.builder()
                .nom(dto.getNom())
                .semestre(dto.getSemestre())
                .nbhCours(dto.getNbhCours())
                .nbhTp(dto.getNbhTp())
                .module(module)
                .build();
        return toDTO(matiereRepository.save(matiere));
    }

    @Override
    public MatiereDTO update(Long id, MatiereDTO dto) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée : " + id));
        if (dto.getModuleId() != null) {
            Module module = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module non trouvé : " + dto.getModuleId()));
            matiere.setModule(module);
        }
        matiere.setNom(dto.getNom());
        matiere.setSemestre(dto.getSemestre());
        matiere.setNbhCours(dto.getNbhCours());
        matiere.setNbhTp(dto.getNbhTp());
        return toDTO(matiereRepository.save(matiere));
    }

    @Override
    public void delete(Long id) {
        if (!matiereRepository.existsById(id))
            throw new EntityNotFoundException("Matière non trouvée : " + id);
        matiereRepository.deleteById(id);
    }

    @Override
    public MatiereDTO affecterModule(Long matiereId, Long moduleId) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée : " + matiereId));
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module non trouvé : " + moduleId));
        matiere.setModule(module);
        return toDTO(matiereRepository.save(matiere));
    }

    // ── Mapper ──
    private MatiereDTO toDTO(Matiere m) {
        return MatiereDTO.builder()
                .id(m.getId())
                .nom(m.getNom())
                .semestre(m.getSemestre())
                .nbhCours(m.getNbhCours())
                .nbhTp(m.getNbhTp())
                .moduleId(m.getModule() != null ? m.getModule().getId() : null)
                .moduleNom(m.getModule() != null ? m.getModule().getNom() : null)
                .build();
    }
}

