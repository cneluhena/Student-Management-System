package com.ctech.sms.service;


import com.ctech.sms.Errors.TeacherNotFoundException;
import com.ctech.sms.entity.Teachers;
import com.ctech.sms.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherRepository teacherRepo;


    //getting teacher by teacherID(not NIC)
    public Teachers findTeacherById(Integer id) throws TeacherNotFoundException {
        Optional<Teachers> optionalTeacher = teacherRepo.findById(id);
        if (optionalTeacher.isEmpty()){
            throw new TeacherNotFoundException("Teacher not Found");
        } else{
            return optionalTeacher.get();
        }

    }


    //getting teacher details by teacher nic
    public Teachers findTeacherByNic(String nic) throws TeacherNotFoundException{
        Optional<Teachers> optionalTeacher = teacherRepo.findByNic(nic);
        if (optionalTeacher.isEmpty()){
            throw new TeacherNotFoundException("Teacher not found!");
        } else{
            return optionalTeacher.get();
        }

    }




}
