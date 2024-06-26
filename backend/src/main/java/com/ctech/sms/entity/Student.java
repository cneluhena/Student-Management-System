package com.ctech.sms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Table(name="student")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private int studentID;
    private String fullName;
    private LocalDate birthDate;
    private String NIC;
    private String fatherName;
    private String motherName;
    private String fatherPhoneNumber;
    private String motherPhoneNumber;
}
