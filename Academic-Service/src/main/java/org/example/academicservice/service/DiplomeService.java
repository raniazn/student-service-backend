package org.example.academicservice.service;

import org.example.academicservice.dto.DiplomeDTO;  // ← bon package
import java.util.List;

public interface DiplomeService {  // ← interface, pas class
    List<DiplomeDTO> getAll();
    List<DiplomeDTO> search(String nom);
    DiplomeDTO getById(Long id);
    DiplomeDTO create(DiplomeDTO dto);
    DiplomeDTO update(Long id, DiplomeDTO dto);
    void delete(Long id);
}