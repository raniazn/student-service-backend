package org.example.teachingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "emplois_temps",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_teacher_type",
                columnNames = {"teacher_id", "type_emploi"}
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploiTemps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Lié à l'entité Teacher existante ──────────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_emploi", nullable = false, length = 30)
    private TypeEmploi typeEmploi;

    @Column(name = "annee_universitaire", nullable = false, length = 20)
    private String anneeUniversitaire;

    @Column(name = "semestre", length = 20)
    private String semestre;

    @Column(name = "pdf_path", length = 255)
    private String pdfPath;

    @OneToMany(
            mappedBy = "emploiTemps",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Seance> seances = new ArrayList<>();
}