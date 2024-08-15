package com.ctech.sms.repository;

import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.CourseDTO;
import com.ctech.sms.entity.TeacherDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    @Query(nativeQuery = true, value = "SELECT grade, array_agg(DISTINCT course_name) as subjects from courses GROUP BY grade")
    List<CourseDTO> getCoursesByGrade();

    @Query(nativeQuery=true, value = "SELECT c.*, t.full_name from courses c Left Join teachers t ON c.teacher_id = t.teacher_id where course_name =?1 and grade=?2")
    List<TeacherDTO> getCourses(String course_name, String grade);

}
