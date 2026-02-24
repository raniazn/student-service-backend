package org.example.academicservice.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleDTO {
    private Long id;
    private String nom;
    private Integer capacite;
}
