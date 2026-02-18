package org.example.teachingservice.repository;

import org.example.teachingservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmail(String email);
}
