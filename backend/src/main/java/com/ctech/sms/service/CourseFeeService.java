package com.ctech.sms.service;


import com.ctech.sms.dto.CourseFeeDTO;
import com.ctech.sms.entity.Course;
import com.ctech.sms.entity.CourseFee;
import com.ctech.sms.repository.CourseFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseFeeService {

    private final CourseFeeRepository courseFeeRepo;
    private final CourseService courseService;

    public CourseFee getCourseFeeByCourseID(Integer courseId) throws Exception{
            CourseFee coursefee = courseFeeRepo.findByCourse_CourseId(courseId);
            if (coursefee != null){
                return coursefee;
            } else{
                throw new Exception("Course fee not found");
            }

    }

    public void addCourseFee(CourseFeeDTO courseFeeDTO) throws Exception{
        try{
            CourseFee coursefee = courseFeeRepo.findByCourse_CourseId(courseFeeDTO.getCourseId());
            if (coursefee == null) {
                Course course = courseService.getCourseByID(courseFeeDTO.getCourseId());
                if (course != null) {
                    CourseFee courseFee = new CourseFee();
                    courseFee.setCourse(course);
                    courseFee.setCoursefee(courseFeeDTO.getCourseFee());
                    courseFeeRepo.save(courseFee);
                } else {
                    throw new Exception("Course not found");
                }
            }
            else{
                throw new Exception("Course fee already exist");
            }
        } catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
