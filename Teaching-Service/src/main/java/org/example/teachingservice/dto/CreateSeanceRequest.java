package org.example.teachingservice.dto;

import org.example.teachingservice.entity.JourSemaine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSeanceRequest {

    @NotNull(message = "Le jour est obligatoire")
    private JourSemaine jour;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    @NotBlank(message = "La matière est obligatoire")
    private String matiere;

    private String typeSeance;   // COURS / TD / TP

    private String salle;

    private String groupe;

    private String niveau;
}