package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;

import com.ctech.sms.entity.Course;

import com.ctech.sms.dto.CourseDTO;
import com.ctech.sms.dto.TeacherDTO;
import com.ctech.sms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Course getCourseByID(Integer courseId) throws  CourseNotFoundException{
        Optional<Course> course = courseRepo.findById(courseId);
        if (course.isPresent()){
            return course.get();
        } else{
            throw new CourseNotFoundException(String.format("Course with ID %d not found", courseId));
        }
    }


    public List<TeacherDTO> getCourses(String course_name, String grade) throws Exception{
        try{
            return courseRepo.getCourses(course_name, grade);
        } catch(Exception e){
            throw new Exception("Error in getting teacher's details");
        }
    }


}
