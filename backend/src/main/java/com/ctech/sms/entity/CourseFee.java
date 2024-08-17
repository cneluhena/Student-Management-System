package com.ctech.sms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name="coursefees")
@AllArgsConstructor
@RequiredArgsConstructor
public class CourseFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseFeeId;
    @OneToOne
    @JoinColumn(name="course_id", nullable = false)
    private Course course;
    private Float coursefee;

}
