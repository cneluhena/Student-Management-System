package com.ctech.sms.repository;

import com.ctech.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByNIC(String nic);
    List<Student> findBystudentId(Integer id);



}