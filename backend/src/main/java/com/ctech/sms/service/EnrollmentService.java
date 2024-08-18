package com.ctech.sms.service;

import com.ctech.sms.Errors.StudentAlreadyEnrolled;
import com.ctech.sms.Errors.StudentNotEnrolled;
import com.ctech.sms.dto.EnrollmentDTO;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Enrollment;
import com.ctech.sms.entity.Student;
import com.ctech.sms.repository.EnrolmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final StudentService studentService;
    private final EnrolmentRepository enrolmentRepo;
    private final CourseService courseService;

    //enrolling a new student to a course
    public void enrollStudent(EnrollmentDTO enrollmentDTO) throws Exception {
        try{

            //check whether the student has already enrolled in the course
            List<Enrollment> currentEnrolments = enrolmentRepo.isStudentEnrolled(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId());
            if (currentEnrolments.isEmpty()){
                int studentId = enrollmentDTO.getStudentId();
                int courseId = enrollmentDTO.getCourseId();
                Student student =  studentService.getStudentById(studentId).get(0);
                Course course = courseService.getCourseByID(courseId);
                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setStudent(student);
                newEnrollment.setCourse(course);
                newEnrollment.setEnrolledDate(LocalDateTime.now());
                enrolmentRepo.save(newEnrollment);
                log.info("Student enrolled successfully!");
            } else{
                throw new StudentAlreadyEnrolled("Student has already enrolled in this course!");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void unEnroll(Integer enrollmentId) throws StudentNotEnrolled {
        Optional<Enrollment> enrollment = enrolmentRepo.findById(enrollmentId);
        if (enrollment.isPresent()){
            enrolmentRepo.deleteById(enrollmentId);
            log.info("Successfully unenrolled");
        } else{
            throw new StudentNotEnrolled("Student has not enrolled in this course");
        }

    }

    public List<Enrollment> getEnrollmentsByStudentId(Integer studentId) throws Exception{
        List<Enrollment> enrollments = enrolmentRepo.findAllByStudent_StudentId(studentId);
        if (!enrollments.isEmpty()){
            return enrollments;
        } else{
            throw new Exception(String.format("No enrollments for this student with id %d", studentId));
        }

    }


}
