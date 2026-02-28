package org.example.teachingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "seances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emploi_temps_id", nullable = false)
    private EmploiTemps emploiTemps;

    @Enumerated(EnumType.STRING)
    @Column(name = "jour", nullable = false, length = 20)
    private JourSemaine jour;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(name = "matiere", nullable = false, length = 150)
    private String matiere;

    @Column(name = "type_seance", length = 50)
    private String typeSeance; // COURS / TD / TP

    @Column(name = "salle", length = 50)
    private String salle;

    @Column(name = "groupe", length = 50)
    private String groupe;

    @Column(name = "niveau", length = 50)
    private String niveau;
}