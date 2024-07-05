package com.ctech.sms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="teachers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {
    @Id
    @Column(name="teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="nic")
    private String nic;


}
