package org.example.academicservice.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column
    private String semestre;

    @Column
    private Integer nbhCours;

    @Column
    private Integer nbhTp;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
