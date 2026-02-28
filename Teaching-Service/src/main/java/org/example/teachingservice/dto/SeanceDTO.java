package org.example.teachingservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeanceDTO {
    private Long id;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String matiere;
    private String typeSeance;
    private String salle;
    private String groupe;
    private String niveau;
}