package com.ctech.sms.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;


public interface CourseDTO {
        String getGrade();
        List<String> getSubjects();


}
