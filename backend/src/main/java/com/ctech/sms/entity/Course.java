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
    private Integer courseId;

    @Column(name="course_name")
    private String courseName;

    @Column(name="grade")
    private String grade;

    @OneToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @Column(name="medium")
    private String medium;

    @Column(name="day")
    private String day;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;

    public Course(String courseName, String grade, Teacher teacher, String medium, String day, LocalTime startTime, LocalTime endTime){
        this.courseName = courseName;
        this.grade= grade;
        this.teacher= teacher;
        this.medium = medium;
        this.day =day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
