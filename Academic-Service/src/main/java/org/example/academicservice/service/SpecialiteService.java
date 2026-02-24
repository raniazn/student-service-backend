package org.example.academicservice.service;

import org.example.academicservice.dto.SpecialiteDTO;  // ← bon package
import java.util.List;

public interface SpecialiteService {  // ← interface, pas class
    List<SpecialiteDTO> getAll();
    List<SpecialiteDTO> search(String nom);
    SpecialiteDTO getById(Long id);
    SpecialiteDTO create(SpecialiteDTO dto);
    SpecialiteDTO update(Long id, SpecialiteDTO dto);
    void delete(Long id);
    SpecialiteDTO affecterModules(Long specialiteId, List<Long> moduleIds);
}