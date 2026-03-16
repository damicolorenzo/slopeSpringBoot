package com.lorenzoproject.slope.controller;

import com.lorenzoproject.slope.dto.OrderDto;
import com.lorenzoproject.slope.dto.PaymentDto;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.request.PaymentRequest;
import com.lorenzoproject.slope.response.ApiResponse;
import com.lorenzoproject.slope.service.order.OrderService;
import com.lorenzoproject.slope.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final OrderService orderService;
    private final IPaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse> initiatePayment (@RequestParam Long orderId, @RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentDto payment = paymentService.initiatePayment(orderId, paymentRequest);
            return ResponseEntity.ok(new ApiResponse("Payment initiated", payment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse> confirmPayment(@RequestParam Long orderId) {
        try {
            orderService.confirmPayment(orderId);
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Payment confirmed successfully", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelPayment(@RequestParam Long orderId) {
        try {
            paymentService.cancelPayment(orderId);
            return ResponseEntity.ok(new ApiResponse("Payment cancelled", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
