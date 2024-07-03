package com.ctech.sms.entity;



import com.ctech.sms.Helper.Time;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Table(name="courses")
public class Course {

    @Id
    private String courseID;
    private String courseName;
    private String grade;
    private Integer teacherID;
    private Time time;
    private LocalTime startTime;
    private LocalTime endTime;


}
