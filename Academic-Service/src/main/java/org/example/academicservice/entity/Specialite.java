package org.example.academicservice.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "specialite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column
    private String niveauSemestre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diplome_id", nullable = false)
    @JsonBackReference
    private Diplome diplome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "specialite_module",
            joinColumns = @JoinColumn(name = "specialite_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    @JsonIgnore
    private List<Module> modules;
}

