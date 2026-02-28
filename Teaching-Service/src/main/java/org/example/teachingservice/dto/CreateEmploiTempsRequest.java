package org.example.teachingservice.dto;

import org.example.teachingservice.entity.TypeEmploi;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmploiTempsRequest {

    @NotNull(message = "L'ID du teacher est obligatoire")
    private Long teacherId;

    @NotNull(message = "Le type d'emploi est obligatoire")
    private TypeEmploi typeEmploi;

    @NotBlank(message = "L'année universitaire est obligatoire")
    private String anneeUniversitaire;

    private String semestre;

    @Valid
    private List<CreateSeanceRequest> seances;
}