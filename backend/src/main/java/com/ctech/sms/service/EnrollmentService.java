package com.ctech.sms.service;

import com.ctech.sms.Errors.StudentAlreadyEnrolled;
import com.ctech.sms.entity.Enrollment;
import com.ctech.sms.repository.EnrolmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

    private final EnrolmentRepository enrolmentRepo;

    //enrolling a new student to a course
    public void enrollStudent(Enrollment enrollment) throws Exception {
        try{
            List<Enrollment> currentEnrolments = enrolmentRepo.isStudentEnrolled(enrollment.getStudentID());
            System.out.println(Arrays.toString(currentEnrolments.toArray()));
            System.out.println(enrollment.getStudentID());


//            if (currentEnrolments.isEmpty()){
//                enrollment.setEnrolledDate(LocalDateTime.now());
//                enrolmentRepo.save(enrollment);
//                log.info("Student enrolled successfully!");
//            } else{
//                throw new StudentAlreadyEnrolled("Student has already enrolled in this course!");
//            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

}
