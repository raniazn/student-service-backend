package org.example.academicservice.dto;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleDTO {
    private Long id;
    private String nom;
    private List<String> specialites; // noms des spécialités liées
}
