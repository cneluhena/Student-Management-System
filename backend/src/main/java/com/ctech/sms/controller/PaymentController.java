package com.ctech.sms.controller;


import com.ctech.sms.Errors.PaymentNotFound;
import com.ctech.sms.dto.PaymentDTO;
import com.ctech.sms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> doPayment(@RequestBody PaymentDTO paymentDTO){
        try{
            paymentService.doPayment(paymentDTO);
            return ResponseEntity.ok().body("Payment Successfull");

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> findPaymentById(@RequestParam(name="id") Integer paymentId){
        try{
            return ResponseEntity.ok().body(paymentService.findPaymentById(paymentId));
        } catch(PaymentNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
