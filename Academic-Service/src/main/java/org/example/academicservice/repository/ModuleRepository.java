package org.example.academicservice.repository;

import org.example.academicservice.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT DISTINCT m FROM Module m LEFT JOIN FETCH m.specialites")
    List<Module> findAllWithSpecialites();
}