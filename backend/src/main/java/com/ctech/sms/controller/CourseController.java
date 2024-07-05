package com.ctech.sms.controller;

import com.ctech.sms.Errors.CourseNotFoundException;

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
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        try{
            courseService.addCourse(course);
            return ResponseEntity.ok().body("Course successfully added");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error occured when adding a course");
        }

    }


    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestParam Integer id, @RequestBody Course course){
        try{
            courseService.updateCourse(id, course);
            return ResponseEntity.ok().body(String.format("course %d is updated", id));
        } catch (CourseNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }



}
