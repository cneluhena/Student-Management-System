package com.ctech.sms.service;


import com.ctech.sms.Errors.StudentAlreadyExist;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Student;
import com.ctech.sms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepo;

    public List<Student> getStudentById(Integer id) throws StudentNotFoundException{
        List<Student> optionalStudent = studentRepo.findBystudentId(id);
        if (!optionalStudent.isEmpty())
            return optionalStudent;
        else
            throw new StudentNotFoundException(String.format("Student with id %d does not exist", id));
    }

    public List<Student> getStudentByNic(String nic) throws StudentNotFoundException{
        List<Student> studentList = studentRepo.findAllByNIC(nic);
        if (!studentList.isEmpty())
            return  studentList;
        else{
            throw new StudentNotFoundException(String.format("Student with nic %s does not exist", nic));
        }

    }

    public void addStudent(Student student) throws StudentAlreadyExist {
        Optional<Student> optionalStudent = studentRepo.findById(student.getStudentId());
        if (optionalStudent.isPresent()){
            log.info("Student already exists..");
            throw new StudentAlreadyExist("Student already exists...");

        } else{
            studentRepo.save(student);
            log.info("Successfully added ...  {}", student.getFullName());
        }

    }


    //updating student details
    public void updateStudent(Integer studentId ,Student student) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepo.findById(studentId);
        if (optionalStudent.isPresent()){
            Student currentStudent = optionalStudent.get();
            if (student.getFullName() != null){
                currentStudent.setFullName(student.getFullName());
            }

            if (student.getBirthDate() != null){
                currentStudent.setBirthDate(student.getBirthDate());
            }

            if (student.getNIC() != null){
                currentStudent.setNIC(student.getNIC());
            }

            if (student.getGuardianName() != null){
                currentStudent.setGuardianName(student.getGuardianName());
            }


            if (student.getGuardianPhoneNumber() != null){
                currentStudent.setGuardianPhoneNumber(student.getGuardianPhoneNumber());;
            }


            studentRepo.save(currentStudent);
            log.info("Student with id {} successfully update", studentId);
        }

        else{
            log.info("Student with id {} not found", studentId);
            throw new StudentNotFoundException(String.format("Student with ID %s not found", studentId));
        }
    }


}
