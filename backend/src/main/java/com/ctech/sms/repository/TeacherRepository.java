package com.ctech.sms.repository;

import com.ctech.sms.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teachers, Integer> {
    Optional<Teachers> findByNic(String nic);  //this will convert the query into sql by jpa
   // List<Teachers> findByteacherID(Integer id);

}