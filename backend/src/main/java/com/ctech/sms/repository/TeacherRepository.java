package com.ctech.sms.repository;

import com.ctech.sms.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByNic(String nic);  //this will convert the query into sql by jpa
   // List<Teachers> findByteacherID(Integer id);

}