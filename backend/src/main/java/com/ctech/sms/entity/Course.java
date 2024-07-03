package com.ctech.sms.entity;


import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Table(name="courses")
public class Course {

    @Id
    private Integer courseID;
    private String courseName;
    private String grade;
    private Integer teacherID;
    private String medium;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;


}
