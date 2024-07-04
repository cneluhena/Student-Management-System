package com.ctech.sms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Data
@Entity
@Table(name="courses")
@AllArgsConstructor
@RequiredArgsConstructor
public class Course {

    @Id
    @Column(name="course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;

    @Column(name="course_name")
    private String courseName;

    @Column(name="grade")
    private String grade;

    @Column(name="teacher_id")
    private Integer teacherID;

    @Column(name="medium")
    private String medium;

    @Column(name="day")
    private String day;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;

}
