package org.example.academicservice.repository;

import org.example.academicservice.entity.Cycle;  // ← bon package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
}