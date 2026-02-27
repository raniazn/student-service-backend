package org.example.academicservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.academicservice.entity.Cycle;      // ← ajouter cet import
import org.example.academicservice.entity.Specialite;
import java.util.List;

@Entity
@Table(name = "diplome")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column
    private String nature;

    @ManyToOne
    @JoinColumn(name = "cycle_id", nullable = false)
    @JsonBackReference
    private Cycle cycle;                               // ← supprimer com.academicservice.entity.Cycle

    @OneToMany(mappedBy = "diplome", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Specialite> specialites;
}