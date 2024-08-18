package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFoundException;

import com.ctech.sms.dto.CourseDTO;
import com.ctech.sms.entity.Course;

import com.ctech.sms.dto.CourseDT;
import com.ctech.sms.dto.TeacherDTO;
import com.ctech.sms.entity.Teacher;
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
    private final TeacherService teacherService;


    //adding a course to the database
    public void addCourse(CourseDTO courseDTO) throws Exception{
        try{
            Teacher teacher = teacherService.findTeacherById(courseDTO.getTeacherId());
            Course newCourse = new Course(courseDTO.getCourseName(),
                    courseDTO.getGrade(),
                    teacher, courseDTO.getMedium(),
                    courseDTO.getDay(),
                    courseDTO.getStartTime(), courseDTO.getEndTime());

            courseRepo.save(newCourse);
            log.info("Successfully added {}", newCourse.getCourseName());
        } catch (Exception e){
            throw new Exception("Error when adding course");
        }
    }

    //updating student details
    public void updateCourse(Integer courseId ,CourseDTO courseDTO) throws Exception {
        try{
            Optional<Course> optionalCourse = courseRepo.findById(courseId);

            if (optionalCourse.isPresent()){
                Course currentCourse = optionalCourse.get();
                if (courseDTO.getCourseName() != null){
                    currentCourse.setCourseName(courseDTO.getCourseName());
                }

                if (courseDTO.getGrade() != null){
                    currentCourse.setGrade(courseDTO.getGrade());
                }

                if (courseDTO.getDay() != null){
                    currentCourse.setDay(courseDTO.getDay());
                }

                if (courseDTO.getStartTime() != null){
                    currentCourse.setStartTime(courseDTO.getStartTime());
                }

                if (courseDTO.getTeacherId() != null){
                    Teacher teacher = teacherService.findTeacherById(courseDTO.getTeacherId());
                    currentCourse.setTeacher(teacher);
                }

                if (courseDTO.getMedium() != null){
                    currentCourse.setMedium(courseDTO.getMedium());
                }

                courseRepo.save(currentCourse);
                log.info("Course with id {} successfully updated", currentCourse.getCourseId());
            }

            else{
                log.info("Course with id {} not found", courseId);
                throw new CourseNotFoundException(String.format("Course with ID %s not found", courseId));
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public List<CourseDT> getCourseByGrade(){
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
