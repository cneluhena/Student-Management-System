package com.ctech.sms.service;


import com.ctech.sms.Errors.StudentAlreadyExist;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.Errors.TeacherAlreadyExist;
import com.ctech.sms.Errors.TeacherNotFoundException;
import com.ctech.sms.entity.Student;
import com.ctech.sms.entity.Teacher;
import com.ctech.sms.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherRepository teacherRepo;


    public void addTeacher(Teacher teacher) throws TeacherAlreadyExist {

        Optional<Teacher> optionalStudent = teacherRepo.findByNic(teacher.getNic());
        if (optionalStudent.isPresent()){
            log.info("Teacher already exists..");
            throw new TeacherAlreadyExist("Teacher already exist..");

        } else{
            teacherRepo.save(teacher);
            log.info("Successfully added ...  {}",teacher.getFullName());
        }

    }

    //getting teacher by teacherID(not NIC)
    public Teacher findTeacherById(Integer id) throws TeacherNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepo.findById(id);
        if (optionalTeacher.isEmpty()){
            throw new TeacherNotFoundException("Teacher not Found");
        } else{
            return optionalTeacher.get();
        }

    }


    //getting teacher details by teacher nic
    public Teacher findTeacherByNic(String nic) throws TeacherNotFoundException{
        Optional<Teacher> optionalTeacher = teacherRepo.findByNic(nic);
        if (optionalTeacher.isEmpty()){
            throw new TeacherNotFoundException("Teacher not found!");
        } else{
            return optionalTeacher.get();
        }

    }

    //updating teacher record
    public void updateTeacher(Integer teacherId, Teacher teacher) throws TeacherNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepo.findById(teacherId);
        if (optionalTeacher.isPresent()){
            Teacher currentTeacher = optionalTeacher.get();
            if (teacher.getFullName() != null)
                currentTeacher.setFullName(teacher.getFullName());

            if (teacher.getNic() != null)
                currentTeacher.setNic(teacher.getNic());

            if (teacher.getEmail() != null)
                currentTeacher.setEmail(teacher.getEmail());

            if (teacher.getPhoneNumber()!=null)
                currentTeacher.setPhoneNumber(teacher.getPhoneNumber());
            teacherRepo.save(currentTeacher);
            log.info("Teacher {} successfully updated", teacherId);
        }

        else{
            log.info("Teacher with id {} not found", teacherId);
            throw new TeacherNotFoundException(String.format("Teacher with ID %d not found", teacherId));
        }
    }

    





}
