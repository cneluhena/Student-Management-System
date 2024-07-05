package com.ctech.sms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Table(name="students")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    @Column(name="student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="nic")
    private String NIC;

    @Column(name="guardian_name")
    private String guardianName;


    @Column(name="guardian_phone_number")
    private String guardianPhoneNumber;

}
