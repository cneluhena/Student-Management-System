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

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepo;

    public List<Student> getStudentById(Integer id) throws StudentNotFoundException{
        List<Student> optionalStudent = studentRepo.findBystudentID(id);
        if (!optionalStudent.isEmpty())
            return optionalStudent;
        else
            throw new StudentNotFoundException(String.format("Student with id %s does not exist", String.valueOf(id)));
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
        Optional<Student> optionalStudent = studentRepo.findById(student.getStudentID());
        if (optionalStudent.isPresent()){
            log.info("Student already exists...");
            throw new StudentAlreadyExist("Student already exists...");

        } else{
            Student saveStudent = studentRepo.save(student);
            log.info("Successfully added {}", student.getFullName());
        }

    }


    //updating student details
    public void updateStudent(Student student) throws StudentNotFoundException {
        int studentId = student.getStudentID();
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

            if (student.getFatherName() != null){
                currentStudent.setFatherName(student.getFatherName());
            }

            if (student.getMotherName() != null){
                currentStudent.setMotherName(student.getMotherName());
            }

            if (student.getFatherPhoneNumber() != null){
                currentStudent.setFatherPhoneNumber(student.getFatherPhoneNumber());
            }

            if (student.getMotherPhoneNumber() != null){
                currentStudent.setMotherPhoneNumber(student.getMotherPhoneNumber());
            }

            studentRepo.save(currentStudent);
            log.info("Student with id {} successfully update", student.getStudentID());
        }

        else{
            log.info("Student with id {} not found", studentId);
            throw new StudentNotFoundException(String.format("Student with ID %s not found", studentId));
        }
    }


}
