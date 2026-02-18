package org.example.studentservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentservice.entity.Payment;
import org.example.studentservice.entity.Student;
import org.example.studentservice.repository.PaymentRepository;
import org.example.studentservice.repository.StudentRepository;
import org.example.studentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/student/{id}")
    public List<Payment> getPayments(@PathVariable Long id){
        return paymentRepository.findByStudentId(id);
    }

    @PostMapping("/{studentId}")
    public Payment addPayment(@PathVariable Long studentId,
                              @RequestBody Payment payment){

        Student student = studentRepository.findById(studentId)
                .orElseThrow();

        payment.setStudent(student);
        return paymentRepository.save(payment);
    }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Long id,
                          @RequestBody Payment p){

        Payment payment = paymentRepository.findById(id).orElseThrow();

        payment.setType(p.getType());
        payment.setMontant(p.getMontant());
        payment.setDate(p.getDate());
        payment.setCheque(p.getCheque());
        payment.setBanque(p.getBanque());

        return paymentRepository.save(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        paymentRepository.deleteById(id);
    }
}


