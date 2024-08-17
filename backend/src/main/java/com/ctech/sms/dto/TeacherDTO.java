package com.ctech.sms.dto;

import java.time.LocalTime;

public interface TeacherDTO {
    int getCourse_id();
    String getCourse_name();
    String getGrade();
    String getTeacher_id();

    String getMedium();

    String getDay();

    LocalTime getStartTime();

    LocalTime getEnd_time();

    String getFull_name();




}
