package com.ctech.sms.dto;


import lombok.Data;

import java.time.LocalTime;


@Data
public class CourseDTO {
    private String courseName;
    private String grade;
    private Integer teacherId;
    private String medium;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
}
