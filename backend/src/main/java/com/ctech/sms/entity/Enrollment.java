package com.ctech.sms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="enrollments")
@AllArgsConstructor
@RequiredArgsConstructor
public class Enrollment {
    @Id
    @Column(name="enrollment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrolmentId;

    @Column(name="student_id")
    private int studentId;

    @Column(name="course_id")
    private Integer courseId;

    @Column(name="enrolled_datetime")
    private LocalDateTime enrolledDate;

}
