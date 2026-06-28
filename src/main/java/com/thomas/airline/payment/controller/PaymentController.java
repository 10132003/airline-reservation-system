package com.thomas.airline.payment.controller;

import com.thomas.airline.payment.dto.PaymentRequestDto;
import com.thomas.airline.payment.dto.PaymentResponseDto;
import com.thomas.airline.payment.dto.PaymentSuccessRequestDto;
import com.thomas.airline.payment.dto.PaymentSuccessResponseDto;
import com.thomas.airline.payment.service.PaymentService;
import com.thomas.airline.payment.service.PaymentWorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private  final PaymentService paymentService;
    private final PaymentWorkflowService paymentWorkflowService;
    public PaymentController(PaymentService paymentService,PaymentWorkflowService paymentWorkflowService) {
        this.paymentService = paymentService;
        this.paymentWorkflowService=paymentWorkflowService;
    }
    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDto requestDto){
        PaymentResponseDto responseDto=paymentService.createPayment(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments(){
        List<PaymentResponseDto> responseDto=paymentService.getAllPayments();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long id){
        PaymentResponseDto responseDto=paymentService.getPaymentById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentRequestDto requestDto){
        PaymentResponseDto responseDto=paymentService.updatePayment(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully.");
    }
    @PostMapping("/complete")
    public ResponseEntity<PaymentSuccessResponseDto> paymentSuccess(@Valid @RequestBody PaymentSuccessRequestDto paymentSuccessRequestDto){
        PaymentSuccessResponseDto paymentSuccessResponseDto=paymentWorkflowService.paymentSuccess(paymentSuccessRequestDto);
        return ResponseEntity.ok(paymentSuccessResponseDto);
    }
}
