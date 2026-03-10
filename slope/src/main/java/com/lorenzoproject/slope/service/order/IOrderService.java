package com.lorenzoproject.slope.service.order;

import com.lorenzoproject.slope.dto.OrderDto;
import com.lorenzoproject.slope.model.Booking;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.model.User;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderService {
    Order createOrder(User user, List<Booking> bookings);
    void confirmPayment(Long orderId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
