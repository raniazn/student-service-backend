package org.example.academicservice.service;

import org.example.academicservice.dto.SalleDTO;  // ← bon package
import java.util.List;

public interface SalleService {  // ← interface, pas class
    List<SalleDTO> getAll();
    List<SalleDTO> search(String nom);
    SalleDTO getById(Long id);
    SalleDTO create(SalleDTO dto);
    SalleDTO update(Long id, SalleDTO dto);
    void delete(Long id);
}