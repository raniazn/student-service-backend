package org.example.academicservice.repository;

import org.example.academicservice.entity.Salle;  // ← corriger ici
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByNomContainingIgnoreCase(String nom);
}