package org.example.academicservice.repository;

import org.example.academicservice.entity.Diplome;  // ← corriger ici
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    List<Diplome> findByCycleId(Long cycleId);
    List<Diplome> findByNomContainingIgnoreCase(String nom);
}