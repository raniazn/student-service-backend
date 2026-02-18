package org.example.studentservice.controller;

import org.example.studentservice.entity.Student;
import org.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public Student add(@RequestBody Student s){

        if(s.getMatricule()==null || s.getMatricule().isEmpty()){
            s.setMatricule("MAT-"+System.currentTimeMillis());
        }

        return studentRepository.save(s);
    }

    // =========================
    // READ ALL  ✅ IMPORTANT
    // =========================
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // =========================
    // READ ONE
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student studentDetails) {

        return studentRepository.findById(id).map(student -> {

            if(studentDetails.getFirstName()!=null)
                student.setFirstName(studentDetails.getFirstName());

            if(studentDetails.getLastName()!=null)
                student.setLastName(studentDetails.getLastName());

            if(studentDetails.getEmail()!=null)
                student.setEmail(studentDetails.getEmail());

            if(studentDetails.getPhoneNum()!=null)
                student.setPhoneNum(studentDetails.getPhoneNum());

            if(studentDetails.getSpeciality()!=null)
                student.setSpeciality(studentDetails.getSpeciality());

            if(studentDetails.getStudentGroup()!=null)
                student.setStudentGroup(studentDetails.getStudentGroup());

            Student updatedStudent = studentRepository.save(student);
            return ResponseEntity.ok(updatedStudent);

        }).orElse(ResponseEntity.notFound().build());
    }


    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
