package org.example.academicservice.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiplomeDTO {
    private Long id;
    private String nom;
    private String nature;
    private Long cycleId;
    private String cycleNom;
}
