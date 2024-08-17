package com.ctech.sms.dto;


import lombok.Data;

@Data
public class PaymentDTO {
    private Integer studentId;
    private Integer courseId;
    private Float amount;


}
