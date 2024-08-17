package com.ctech.sms.repository;


import com.ctech.sms.entity.CourseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CourseFeeRepository extends JpaRepository<CourseFee, Integer> {
        CourseFee findByCourse_CourseId(Integer course_id);

}
