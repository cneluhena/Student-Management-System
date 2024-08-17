package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;
import com.ctech.sms.Errors.PaymentNotFound;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Payment;
import com.ctech.sms.entity.PaymentDTO;
import com.ctech.sms.entity.Student;
import com.ctech.sms.repository.CourseRepository;
import com.ctech.sms.repository.PaymentRepository;
import com.ctech.sms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final PaymentRepository paymentRepo;
    public void doPayment(PaymentDTO paymentDTO) throws StudentNotFoundException, CourseNotFoundException {

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
                paymentRepo.save(payment);
            }

        } catch(StudentNotFoundException | CourseNotFoundException e){
            throw e;
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
