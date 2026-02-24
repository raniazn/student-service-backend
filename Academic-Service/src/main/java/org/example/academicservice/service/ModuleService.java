package org.example.academicservice.service;

import org.example.academicservice.dto.ModuleDTO;  // ← bon package
import java.util.List;

public interface ModuleService {   // ← interface, pas class
    List<ModuleDTO> getAll();
    List<ModuleDTO> search(String nom);
    ModuleDTO getById(Long id);
    ModuleDTO create(ModuleDTO dto);
    ModuleDTO update(Long id, ModuleDTO dto);
    void delete(Long id);
}