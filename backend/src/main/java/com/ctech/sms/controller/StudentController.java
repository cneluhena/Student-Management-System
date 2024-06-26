package com.ctech.sms.controller;

import com.ctech.sms.Errors.StudentAlreadyExist;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Student;
import com.ctech.sms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/")
    public ResponseEntity<String> addStudent(@RequestBody Student student){
        try{
            studentService.addStudent(student);
            return ResponseEntity.ok().body("Student successfully added");
        } catch (StudentAlreadyExist e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");
        }

    }


    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestParam Integer id, @RequestBody Student student){
        try{
            studentService.updateStudent(id, student);
            return ResponseEntity.ok().body(String.format("Student %d is updated", id));
        } catch (StudentNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/find")
    public ResponseEntity<?> getStudent (
            @RequestParam(name="id", required = false) Integer Id,
            @RequestParam(name="nic", required = false) String nic) {
        try {
            if (Id != null)
                return ResponseEntity.ok().body(studentService.getStudentById(Id));
            else if (nic != null)
                return ResponseEntity.ok().body(studentService.getStudentByNic(nic));
            else
                throw new StudentNotFoundException("Error");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getStudent (
            @RequestParam(name="id", required = true) Integer Id){
        try {
            if (Id != null)
                return ResponseEntity.ok().body(studentService.getStudentById(Id));
            else
                throw new StudentNotFoundException("Error");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    }

