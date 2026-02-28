package org.example.teachingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emploi_formation_soir")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploiFormationSoir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enseignant_id")
    private Long enseignantId;

    @Column(name = "enseignant_nom")
    private String enseignantNom;

    @Column(name = "matiere")
    private String matiere;

    @Column(name = "groupe")
    private String groupe;

    @Column(name = "semestre")
    private String semestre;

    @Column(name = "salle")
    private String salle;

    @Column(name = "type_formation")
    private String typeFormation; // "SOIR"
}