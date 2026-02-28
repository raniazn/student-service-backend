package org.example.teachingservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploiFormationJourDTO {
    private Long id;
    private Long enseignantId;
    private String enseignantNom;
    private String matiere;
    private String groupe;
    private String semestre;
    private String salle;
    private String typeFormation;
}