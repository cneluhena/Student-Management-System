package com.ctech.sms.controller;

import com.ctech.sms.Errors.CourseNotFoundException;
import com.ctech.sms.Errors.StudentAlreadyExist;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Student;
import com.ctech.sms.service.CourseService;
import com.ctech.sms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        try{
            courseService.addCourse(course);
            return ResponseEntity.ok().body("Course successfully added");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error occured when adding a course");
        }

    }


    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestParam Integer courseId, @RequestBody Course course){
        try{
            courseService.updateCourse(courseId, course);
            return ResponseEntity.ok().body(String.format("course %d is updated", courseId));
        } catch (CourseNotFoundException e){
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
