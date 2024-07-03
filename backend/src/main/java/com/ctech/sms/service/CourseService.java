package com.ctech.sms.service;

import com.ctech.sms.Errors.CourseNotFound;
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

    public List<Course> getStudentById(Integer id) throws StudentNotFoundException {
        List<Student> optionalStudent = studentRepo.findBystudentID(id);
        if (!optionalStudent.isEmpty())
            return optionalStudent;
        else
            throw new StudentNotFoundException(String.format("Student with id %d does not exist", id));
    }

    public List<Student> getStudentByNic(String nic) throws StudentNotFoundException{
        List<Student> studentList = studentRepo.findAllByNIC(nic);
        if (!studentList.isEmpty())
            return  studentList;
        else{
            throw new StudentNotFoundException(String.format("Student with nic %s does not exist", nic));
        }

    }

    //adding a course to the database
    public void addCourse(Course course) throws StudentAlreadyExist {
            courseRepo.save(course);
            log.info("Successfully added {}", course.getCourseName());

    }

    //updating student details
    public void updateCourse(String courseId ,Course course) throws CourseNotFound {
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

            if (course.getTime() != null){
                currentCourse.setTime(course.getTime());
            }

            if (course.getTeacherID() != null){
                currentCourse.setTeacherID(course.getTeacherID());
            }


            courseRepo.save(currentCourse);
            log.info("Course with id {} successfully updated", currentCourse.getCourseID());
        }

        else{
            log.info("Student with id {} not found", course.getCourseID());
            throw new CourseNotFoundException(String.format("Student with ID %s not found", ));
        }
    }


}
