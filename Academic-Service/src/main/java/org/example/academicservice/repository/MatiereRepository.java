package org.example.academicservice.repository;

import org.example.academicservice.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    // 🔥 Charger module avec la matière
    @Query("SELECT m FROM Matiere m LEFT JOIN FETCH m.module")
    List<Matiere> findAllWithModule();

    // 🔥 Recherche avec module
    @Query("""
           SELECT m FROM Matiere m
           LEFT JOIN FETCH m.module
           WHERE LOWER(m.nom) LIKE LOWER(CONCAT('%', :nom, '%'))
           """)
    List<Matiere> searchWithModule(@Param("nom") String nom);

    List<Matiere> findByModuleId(Long moduleId);

    List<Matiere> findBySemestre(String semestre);
}