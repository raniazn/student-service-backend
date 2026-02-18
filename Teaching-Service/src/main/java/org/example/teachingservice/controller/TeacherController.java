package org.example.teachingservice.controller;

import org.example.teachingservice.entity.Teacher;
import org.example.teachingservice.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teachers")


public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;
    //Create
    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }
    // READ ALL
    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        return teacherRepository.findById(id).map(teacher -> {
            teacher.setLastName(teacherDetails.getLastName());
            teacher.setFirstName(teacherDetails.getFirstName());
            teacher.setEmail(teacherDetails.getEmail());
            teacher.setPhoneNum(teacherDetails.getPhoneNum());
            teacher.setSpeciality(teacherDetails.getSpeciality());
            teacher.setTeacherGroup(teacherDetails.getTeacherGroup());
            Teacher updatedTeacher = teacherRepository.save(teacher);
            return ResponseEntity.ok(updatedTeacher);
        }).orElse(ResponseEntity.notFound().build());
    }
    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
