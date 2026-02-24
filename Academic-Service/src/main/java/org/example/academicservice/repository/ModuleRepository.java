package org.example.academicservice.repository;

import org.example.academicservice.entity.Module;   // ← bon package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {  // ← extends manquant
    List<Module> findByNomContainingIgnoreCase(String nom);
}