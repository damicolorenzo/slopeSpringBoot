package com.lorenzoproject.slope.service.payment;

import com.lorenzoproject.slope.enums.OrderStatus;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.repository.OrderRepository;
import com.lorenzoproject.slope.service.order.OrderService;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private String stripeSecretKey;
    private String webhookSecret;
    private String successUrl;
    private String cancelUrl;

    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public String createCheckoutSession(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if(order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order is not payable");
        }

        SessionCreateParams params = SessionCreateParams
                .builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?orderId=" + orderId)
                .setCancelUrl(cancelUrl)
                .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("eur")
                                        .setUnitAmount(order.getTotalAmount()
                                                .multiply(BigDecimal.valueOf(100))
                                                .longValue()
                                        )
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Ski Booking")
                                                .build()
                                        )
                                        .build()
                                )
                                .build()
                )
                .putMetadata("orderId", orderId.toString())
                .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch(Exception e) {
            throw new RuntimeException("Stripe error", e);
        }
    }

    @Override
    public void handleStripeWebhook(String payload, String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            throw new RuntimeException("Invalid webhook", e);
        }

        if("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow();

            Long orderId = Long.parseLong(session.getMetadata().get("orderId"));

            orderService.confirmPayment(orderId);
        }
    }
}
