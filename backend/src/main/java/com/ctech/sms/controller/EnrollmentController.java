package com.ctech.sms.controller;


import com.ctech.sms.Errors.StudentAlreadyEnrolled;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Enrollment;
import com.ctech.sms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;


    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestBody Enrollment enrollment){
        try{
            enrollmentService.enrollStudent(enrollment);
            return ResponseEntity.ok().body("Student Enrolled successfully");
        } catch(StudentAlreadyEnrolled se){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(se.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error occured enrolling");
        }

    }

}
