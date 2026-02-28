package org.example.teachingservice.dto;

import org.example.teachingservice.entity.TypeEmploi;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploiTempsDTO {
    private Long id;
    private Long teacherId;
    private String teacherLastName;
    private String teacherFirstName;
    private String teacherMatricule;
    private String teacherIdCard;        // CIN dans votre Teacher
    private TypeEmploi typeEmploi;
    private String anneeUniversitaire;
    private String semestre;
    private String pdfPath;
    private List<SeanceDTO> seances;
}