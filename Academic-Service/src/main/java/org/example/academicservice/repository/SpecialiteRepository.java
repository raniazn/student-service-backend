package org.example.academicservice.repository;

import org.example.academicservice.entity.Specialite;  // ← bon package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    List<Specialite> findByDiplomeId(Long diplomeId);
    List<Specialite> findByNomContainingIgnoreCase(String nom);
}