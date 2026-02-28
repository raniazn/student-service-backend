package org.example.teachingservice.repository;

import org.example.teachingservice.entity.EmploiTemps;
import org.example.teachingservice.entity.TypeEmploi;
import org.example.teachingservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmploiTempsRepository extends JpaRepository<EmploiTemps, Long> {

    // ===============================
    // Méthodes simples
    // ===============================

    List<EmploiTemps> findByTeacherId(Long teacherId);

    List<EmploiTemps> findByTypeEmploi(TypeEmploi typeEmploi);

    Optional<EmploiTemps> findByTeacherIdAndTypeEmploi(Long teacherId, TypeEmploi typeEmploi);

    boolean existsByTeacherIdAndTypeEmploi(Long teacherId, TypeEmploi typeEmploi);


    // ===============================
    // SOLUTION LazyInitializationException
    // ===============================

    @Query("""
           SELECT e
           FROM EmploiTemps e
           JOIN FETCH e.teacher
           WHERE e.typeEmploi = :type
           """)
    List<EmploiTemps> findByTypeWithTeacher(@Param("type") TypeEmploi type);


    // ===============================
    // Avec seances + teacher
    // ===============================

    @Query("""
           SELECT et FROM EmploiTemps et
           JOIN FETCH et.teacher
           JOIN FETCH et.seances
           WHERE et.teacher.id = :teacherId
           AND et.typeEmploi = :typeEmploi
           """)
    Optional<EmploiTemps> findWithSeancesByTeacherIdAndType(
            @Param("teacherId") Long teacherId,
            @Param("typeEmploi") TypeEmploi typeEmploi);


    @Query("""
           SELECT DISTINCT et FROM EmploiTemps et
           JOIN FETCH et.teacher
           LEFT JOIN FETCH et.seances
           WHERE et.typeEmploi = :typeEmploi
           """)
    List<EmploiTemps> findAllWithSeancesByType(
            @Param("typeEmploi") TypeEmploi typeEmploi);


    // ===============================
    // Teachers par type
    // ===============================

    @Query("SELECT DISTINCT et.teacher FROM EmploiTemps et WHERE et.typeEmploi = 'SEMESTRE_1'")
    List<Teacher> findTeachersBySemestre1();

    @Query("SELECT DISTINCT et.teacher FROM EmploiTemps et WHERE et.typeEmploi = 'SEMESTRE_2'")
    List<Teacher> findTeachersBySemestre2();

    @Query("SELECT DISTINCT et.teacher FROM EmploiTemps et WHERE et.typeEmploi = 'FORMATION_JOUR'")
    List<Teacher> findTeachersByFormationJour();

    @Query("SELECT DISTINCT et.teacher FROM EmploiTemps et WHERE et.typeEmploi = 'FORMATION_SOIR'")
    List<Teacher> findTeachersByFormationSoir();

    @Query("SELECT DISTINCT et.teacher FROM EmploiTemps et WHERE et.typeEmploi = 'WEEK_END'")
    List<Teacher> findTeachersByWeekEnd();
}