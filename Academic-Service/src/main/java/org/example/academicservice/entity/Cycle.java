package org.example.academicservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "cycle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "cycle", cascade = CascadeType.ALL)
    private List<Diplome> diplomes;  // ← supprimer com.academicservice.entity.Diplome
}