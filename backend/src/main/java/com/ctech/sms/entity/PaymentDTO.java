package com.ctech.sms.entity;


import lombok.Data;

@Data
public class PaymentDTO {
    private Integer studentId;
    private Integer courseId;
    private Float amount;


}
