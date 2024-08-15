package com.ctech.sms.repository;

import com.ctech.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByNIC(String nic);
    List<Student> findBystudentId(Integer id);

    @Query(nativeQuery = true,
            value = "Select * FROM students where full_name ILIKE ?1")
    List<Student> findByName(String name);



}