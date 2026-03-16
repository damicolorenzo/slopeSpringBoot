package com.lorenzoproject.slope.service.payment;

import com.lorenzoproject.slope.dto.PaymentDto;
import com.lorenzoproject.slope.enums.BookingStatus;
import com.lorenzoproject.slope.enums.OrderStatus;
import com.lorenzoproject.slope.enums.PaymentStatus;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.model.Payment;
import com.lorenzoproject.slope.repository.OrderRepository;
import com.lorenzoproject.slope.repository.PaymentRepository;
import com.lorenzoproject.slope.request.PaymentRequest;
import com.lorenzoproject.slope.service.order.OrderService;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override

    public PaymentDto initiatePayment(Long orderId, PaymentRequest request) {
        // Find the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Check if order is already paid
        if (order.getOrderStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("Order is already paid");
        }

        // Check if payment already exists
        Optional<Payment> existingPayment = paymentRepository.findByOrderId(orderId);
        if (existingPayment.isPresent() &&
                existingPayment.get().getStatus() == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Payment already completed for this order");
        }

        // Create payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(generateTransactionId());

        Payment savedPayment = paymentRepository.save(payment);

        return convertToDto(savedPayment);
    }

    @Override

    public PaymentDto processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // Update payment status
        payment.setStatus(PaymentStatus.PROCESSING);

        try {
            // Here you would integrate with actual payment gateway
            // For now, we'll simulate successful payment
            boolean paymentSuccessful = processWithPaymentGateway(payment);

            if (paymentSuccessful) {
                payment.setStatus(PaymentStatus.COMPLETED);
                payment.setPaymentDate(LocalDateTime.now());

                // Update order status
                Order order = payment.getOrder();
                order.setOrderStatus(OrderStatus.PAID);
                order.getBookings().forEach(b -> b.setStatus(BookingStatus.CONFIRMED));
                orderRepository.save(order);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }

        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return convertToDto(updatedPayment);
    }

    @Override
    public void cancelPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed payment");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);

        // Update order status
        Order order = payment.getOrder();
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public PaymentDto getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order"));
        return convertToDto(payment);
    }

    @Override
    public PaymentDto convertToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    // Helper methods
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private boolean processWithPaymentGateway(Payment payment) {
        // Simulate payment gateway call
        // In production, integrate with Stripe, PayPal, etc.
        return true;  // Simulated success
    }
}