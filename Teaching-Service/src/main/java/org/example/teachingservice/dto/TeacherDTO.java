package org.example.teachingservice.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private Long id;
    private String matricule;
    private String lastName;
    private String firstName;
    private String idCard;
    private LocalDate deliveredOn;
    private String email;
    private String sex;
    private LocalDate birthDay;
    private String nationality;
    private String phoneNum;
    private String speciality;
    private String teacherGroup;
    private boolean hasEmploi;
}