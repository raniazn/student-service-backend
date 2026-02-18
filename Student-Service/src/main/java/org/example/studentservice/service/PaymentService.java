package org.example.studentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.studentservice.entity.Payment;
import org.example.studentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;

    public Payment add(Payment p){
        return repo.save(p);
    }

    public List<Payment> getAll(){
        return repo.findAll();
    }

    public List<Payment> getByStudent(Long studentId){
        return repo.findByStudentId(studentId);
    }

    public Payment update(Long id, Payment p){
        p.setId(id);
        return repo.save(p);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
