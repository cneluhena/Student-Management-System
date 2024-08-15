package com.ctech.sms.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;


public interface CourseDTO {
        String getGrade();
        List<String> getSubjects();


}
