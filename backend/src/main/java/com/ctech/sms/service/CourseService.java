package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;
import com.ctech.sms.Errors.StudentAlreadyExist;
import com.ctech.sms.Errors.StudentNotFoundException;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.Student;
import com.ctech.sms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepo;




    //adding a course to the database
    public void addCourse(Course course){
        try{
            courseRepo.save(course);
            log.info("Successfully added {}", course.getCourseName());
        } catch (Exception e){
            log.info("Error Occurred");
        }


    }

    //updating student details
    public void updateCourse(Integer courseId ,Course course) throws CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepo.findById(Integer.toString(courseId));
        if (optionalCourse.isPresent()){
            Course currentCourse = optionalCourse.get();
            if (course.getCourseName() != null){
                currentCourse.setCourseName(course.getCourseName());
                currentCourse.setCourseName(course.getCourseName());
            }

            if (course.getGrade() != null){
                currentCourse.setGrade(course.getGrade());
            }

            if (course.getDay() != null){
                currentCourse.setDay(course.getDay());
            }

            if (course.getStartTime() != null){
                currentCourse.setStartTime(course.getStartTime());
            }

            if (course.getTeacherID() != null){
                currentCourse.setTeacherID(course.getTeacherID());
            }

            if (course.getMedium() != null){
                currentCourse.setMedium(course.getMedium());
            }


            courseRepo.save(currentCourse);
            log.info("Course with id {} successfully updated", currentCourse.getCourseID());
        }

        else{
            log.info("Student with id {} not found", course.getCourseID());
            throw new CourseNotFoundException(String.format("Course with ID %s not found", course.getCourseID()));
        }
    }


}
