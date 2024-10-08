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

    @PostMapping("/add")
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
    public ResponseEntity<?> findStudent (
            @RequestParam(name="id", required = false) Integer Id,
            @RequestParam(name="nic", required = false) String nic,
            @RequestParam(name="name", required = false) String name) {
        try {
            if (Id != null)
                return ResponseEntity.ok().body(studentService.getStudentById(Id));  //finding the student by studentId

            else if (nic != null)
                return ResponseEntity.ok().body(studentService.getStudentByNic(nic)); //finding the student by nic

            else if (name != null)
                return ResponseEntity.ok().body(studentService.getStudentByName(name));
            else
                throw new StudentNotFoundException("Student Not Found");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getStudent (
            @RequestParam(name="id") Integer Id){

        try {
            return ResponseEntity.ok().body(studentService.getStudentById(Id));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    }

