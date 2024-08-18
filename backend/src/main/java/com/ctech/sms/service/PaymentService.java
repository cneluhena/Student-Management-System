package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;
import com.ctech.sms.Errors.PaymentNotFound;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Payment;
import com.ctech.sms.dto.PaymentDTO;
import com.ctech.sms.entity.Student;
import com.ctech.sms.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final PaymentRepository paymentRepo;
    public void doPayment(PaymentDTO paymentDTO) throws Exception {

        try{
            List<Student> student = studentService.getStudentById(paymentDTO.getStudentId());
            System.out.println(paymentDTO.getStudentId());
            Course course = courseService.getCourseByID(paymentDTO.getCourseId());
            Float amount = paymentDTO.getAmount();
            if (amount != null && amount > 0){
                Payment payment = new Payment();
                payment.setCourse(course);
                payment.setStudent(student.get(0));
                payment.setPaymentAmount(amount);
                payment.setPaymentDate(LocalDateTime.now());
                paymentRepo.save(payment);
            } else{
                throw new Exception("Amount cannot be empty");
            }

        } catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }


    //finding a payment by payment ID
    public Payment findPaymentById(Integer paymentId) throws PaymentNotFound{
        Payment payment = paymentRepo.findByPaymentId(paymentId);
        if (payment != null){
            return payment;

        } else{
            throw new PaymentNotFound(String.format("Payment with id %d not found", paymentId));
        }
    }

}
