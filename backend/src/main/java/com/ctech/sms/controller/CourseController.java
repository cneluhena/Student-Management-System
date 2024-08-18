package com.ctech.sms.controller;

import com.ctech.sms.Errors.CourseNotFoundException;

import com.ctech.sms.dto.CourseDTO;
import com.ctech.sms.entity.Course;

import com.ctech.sms.service.CourseService;
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
    public ResponseEntity<String> addCourse(@RequestBody CourseDTO courseDTO ){
        try{
            courseService.addCourse(courseDTO);
            return ResponseEntity.ok().body("Course successfully added");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error occurred when adding a course");
        }

    }


    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestParam Integer id, @RequestBody CourseDTO courseDTO){
        try{
            courseService.updateCourse(id, courseDTO);
            return ResponseEntity.ok().body(String.format("course %d is updated", id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/get")
    public ResponseEntity<?>  getCourse() throws Exception{
        try{
            return ResponseEntity.ok().body(courseService.getCourseByGrade());
        } catch(Exception e){
            throw new Exception("not found");
        }
    }

    @GetMapping("/getteachers")
    public ResponseEntity<?> getTeachersName(@RequestParam(required = true, name="course") String course_name,
                                             @RequestParam(required = true, name="grade") String grade){
        try{
            return ResponseEntity.ok().body(courseService.getCourses(course_name, grade));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



}
