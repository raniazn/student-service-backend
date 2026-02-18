package org.example.teachingservice.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "teachers" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricule;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "delivered_on")
    private LocalDate deliveredOn;

    @Column(unique = true)
    private String email;

    private String sex;
    @Column(name = "birth_day")
    private LocalDate birthDay;

    private String nationality;

    @Column(name = "phone_num")
    private String phoneNum;

    private String speciality;

    @Column(name = "teacher_group")
    private String teacherGroup;

}
