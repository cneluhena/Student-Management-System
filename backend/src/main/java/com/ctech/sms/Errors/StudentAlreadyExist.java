package com.ctech.sms.Errors;

public class StudentAlreadyExist extends Exception{
    public StudentAlreadyExist(String message){
        super(message);
    }
}
