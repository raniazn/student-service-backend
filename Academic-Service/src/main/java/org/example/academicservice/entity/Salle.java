package org.example.academicservice.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column
    private Integer capacite;
}