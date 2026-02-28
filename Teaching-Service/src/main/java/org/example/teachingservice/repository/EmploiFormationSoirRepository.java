package org.example.teachingservice.repository;

import org.example.teachingservice.entity.EmploiFormationSoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmploiFormationSoirRepository extends JpaRepository<EmploiFormationSoir, Long> {

    List<EmploiFormationSoir> findByTypeFormation(String typeFormation);

    List<EmploiFormationSoir> findByEnseignantNomContainingIgnoreCaseOrMatiereContainingIgnoreCaseOrGroupeContainingIgnoreCase(
            String enseignant, String matiere, String groupe
    );
}