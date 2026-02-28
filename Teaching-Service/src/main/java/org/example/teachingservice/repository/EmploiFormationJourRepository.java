package org.example.teachingservice.repository;

import org.example.teachingservice.entity.EmploiFormationJour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploiFormationJourRepository extends JpaRepository<EmploiFormationJour, Long> {

    List<EmploiFormationJour> findByTypeFormation(String typeFormation);

    List<EmploiFormationJour> findByEnseignantNomContainingIgnoreCaseOrMatiereContainingIgnoreCaseOrGroupeContainingIgnoreCase(
            String enseignant, String matiere, String groupe
    );
}