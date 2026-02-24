package org.example.academicservice.serviceImpl;

import org.example.academicservice.dto.ModuleDTO;
import org.example.academicservice.entity.Module;
import org.example.academicservice.repository.ModuleRepository;
import org.example.academicservice.service.ModuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public List<ModuleDTO> getAll() {
        return moduleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModuleDTO> search(String nom) {
        return moduleRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModuleDTO getById(Long id) {
        return toDTO(
                moduleRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Module non trouvé : " + id))
        );
    }

    @Override
    public ModuleDTO create(ModuleDTO dto) {
        Module module = Module.builder()
                .nom(dto.getNom())
                .build();
        return toDTO(moduleRepository.save(module));
    }

    @Override
    public ModuleDTO update(Long id, ModuleDTO dto) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Module non trouvé : " + id));

        module.setNom(dto.getNom());
        return toDTO(moduleRepository.save(module));
    }

    @Override
    public void delete(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Module non trouvé : " + id);
        }
        moduleRepository.deleteById(id);
    }

    private ModuleDTO toDTO(Module m) {
        List<String> specs =
                (m.getSpecialites() == null)
                        ? List.of()
                        : m.getSpecialites()
                        .stream()
                        .map(s -> s.getNom())
                        .collect(Collectors.toList());

        return ModuleDTO.builder()
                .id(m.getId())
                .nom(m.getNom())
                .specialites(specs)
                .build();
    }
}