package org.example.academicservice.repository;

import org.example.academicservice.entity.Matiere;  // ← bon package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    List<Matiere> findByModuleId(Long moduleId);
    List<Matiere> findBySemestre(String semestre);
    List<Matiere> findByNomContainingIgnoreCase(String nom);
}