package org.example.academicservice.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatiereDTO {
    private Long id;
    private String nom;
    private String semestre;
    private Integer nbhCours;
    private Integer nbhTp;
    private Long moduleId;
    private String moduleNom;
}
