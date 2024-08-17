package com.ctech.sms.controller;


import com.ctech.sms.Errors.StudentAlreadyEnrolled;
import com.ctech.sms.dto.EnrollmentDTO;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Enrollment;
import com.ctech.sms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;


    @PostMapping("/add")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollmentDTO enrollmentDTO){
        try{
            enrollmentService.enrollStudent(enrollmentDTO);
            return ResponseEntity.ok().body("Student Enrolled successfully");
        } catch(Exception se){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(se.getMessage());
        }

    }

    @GetMapping("/getStudent")
    public ResponseEntity<?> getEnrollmentsByStudentId(@RequestParam(name="id") Integer studentId){
        try{
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
            return ResponseEntity.ok().body(enrollments);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> unEnrollStudent(@RequestParam Integer id){
        try{
            enrollmentService.unEnroll(id);
            return ResponseEntity.ok().body("Student unenrolled successfully");
        } catch(Exception se){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(se.getMessage());
        }

    }



}
