package org.example.academicservice.service;

import org.example.academicservice.dto.MatiereDTO;  // ← bon package
import java.util.List;

public interface MatiereService {  // ← interface, pas class
    List<MatiereDTO> getAll();
    List<MatiereDTO> search(String nom);
    List<MatiereDTO> getByModule(Long moduleId);
    MatiereDTO getById(Long id);
    MatiereDTO create(MatiereDTO dto);
    MatiereDTO update(Long id, MatiereDTO dto);
    void delete(Long id);
    MatiereDTO affecterModule(Long matiereId, Long moduleId);
}