package com.ctech.sms.repository;


import com.ctech.sms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrolmentRepository extends JpaRepository<Enrollment, Integer> {
    @Query(
            nativeQuery = true,
            value= "Select * FROM enrollments WHERE (student_id=?1 AND course_id=?2)")
    List<Enrollment> isStudentEnrolled(Integer studentId, Integer courseId);

}
