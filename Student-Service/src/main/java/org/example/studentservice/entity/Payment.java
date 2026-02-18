package org.example.studentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name="payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Double montant;
    private LocalDate date;
    private String cheque;
    private String banque;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;
}
