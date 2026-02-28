package org.example.teachingservice.repository;

import org.example.teachingservice.entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    List<Seance> findByEmploiTempsId(Long emploiTempsId);

    @Query("SELECT s FROM Seance s " +
            "WHERE s.emploiTemps.teacher.id = :teacherId " +
            "ORDER BY s.jour, s.heureDebut")
    List<Seance> findAllByTeacherIdOrderByJourAndHeure(@Param("teacherId") Long teacherId);
}