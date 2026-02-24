package org.example.academicservice.dto;
import lombok.*;
import java.util.List;

import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SpecialiteDTO {
    private Long id;
    private String nom;
    private String niveauSemestre;
    private Long diplomeId;
    private String diplomeNom;
    private String cycleNom;
    private List<Long> moduleIds;
}
