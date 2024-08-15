package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;

import com.ctech.sms.entity.Course;

import com.ctech.sms.entity.CourseDTO;
import com.ctech.sms.entity.TeacherDTO;
import com.ctech.sms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepo;


    //adding a course to the database
    public void addCourse(Course course) throws Exception{
        try{
            courseRepo.save(course);
            log.info("Successfully added {}", course.getCourseName());
        } catch (Exception e){
            throw new Exception("Error when adding course");
        }
    }

    //updating student details
    public void updateCourse(Integer courseId ,Course course) throws CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepo.findById(courseId);
        if (optionalCourse.isPresent()){
            Course currentCourse = optionalCourse.get();
            if (course.getCourseName() != null){
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

            if (course.getTeacherId() != null){
                currentCourse.setTeacherId(course.getTeacherId());
            }

            if (course.getMedium() != null){
                currentCourse.setMedium(course.getMedium());
            }


            courseRepo.save(currentCourse);
            log.info("Course with id {} successfully updated", currentCourse.getCourseId());
        }

        else{
            log.info("Course with id {} not found", course.getCourseId());
            throw new CourseNotFoundException(String.format("Course with ID %s not found", course.getCourseId()));
        }
    }

    public List<CourseDTO> getCourseByGrade(){
        return courseRepo.getCoursesByGrade();
    }

    public List<TeacherDTO> getTeachersOfCourse(String course_name, String grade){
        return courseRepo.getTeachersId(course_name, grade);
    }


}
