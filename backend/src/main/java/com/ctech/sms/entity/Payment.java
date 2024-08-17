package com.ctech.sms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="payments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @Column(name="payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name="student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @Column(name="payment_amount")
    private Float paymentAmount;








}
