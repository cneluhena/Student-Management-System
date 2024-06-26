package com.ctech.sms.controller;


import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.Errors.TeacherNotFoundException;
import com.ctech.sms.entity.Student;
import com.ctech.sms.entity.Teacher;
import com.ctech.sms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/find")
    public ResponseEntity<?> getTeacher (
            @RequestParam(name="id", required = false) Integer Id,
            @RequestParam(name="nic", required = false) String nic) throws StudentNotFoundException {
        try {
            if (Id != null)
                return ResponseEntity.ok().body(teacherService.findTeacherById(Id));
            else if (nic != null)
                return ResponseEntity.ok().body(teacherService.findTeacherByNic(nic));
            else
                throw new StudentNotFoundException("Error");
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestParam int id, @RequestBody Teacher teacher){
        try{
            teacherService.updateTeacher(id, teacher);
            return ResponseEntity.ok().body(String.format("Teacher %s is updated", id));
        } catch (TeacherNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


}
