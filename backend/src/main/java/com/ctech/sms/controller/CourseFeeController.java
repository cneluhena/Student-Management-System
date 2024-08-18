package com.ctech.sms.controller;


import com.ctech.sms.dto.CourseFeeDTO;
import com.ctech.sms.service.CourseFeeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/coursefee")
@RequiredArgsConstructor
public class CourseFeeController {

    private final CourseFeeService courseFeeService;

    @GetMapping("/get")
    public ResponseEntity<?> getCourseFee(@RequestParam(name="id") Integer courseId){
        try{
            return ResponseEntity.ok().body(courseFeeService.getCourseFeeByCourseID(courseId));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PostMapping("/add")
    public ResponseEntity<String> addCourseFee(@RequestBody CourseFeeDTO courseFeeDTO){
        try{
            if (courseFeeDTO.getCourseId() !=null && courseFeeDTO.getCourseFee() != null) {
                courseFeeService.addCourseFee(courseFeeDTO);
                return ResponseEntity.ok().body("Course fee successfully added");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somehitng went wrong");
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
}
