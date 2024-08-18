package com.ctech.sms.Errors;

public class PaymentNotFound extends Exception{
    public PaymentNotFound(String message){
        super(message);
    }
}
