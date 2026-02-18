package org.example.studentservice.repository;

import org.example.studentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long>{

    List<Payment> findByStudentId(Long studentId);

}
