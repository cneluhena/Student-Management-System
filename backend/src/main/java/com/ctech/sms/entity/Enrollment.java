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
    private Integer enrollmentId;

    @ManyToOne
    @JoinColumn(name="student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @Column(name="enrolled_datetime")
    private LocalDateTime enrolledDate;

}
