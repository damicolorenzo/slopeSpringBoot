package com.lorenzoproject.slope.service.order;

import com.lorenzoproject.slope.dto.OrderDto;
import com.lorenzoproject.slope.enums.BookingStatus;
import com.lorenzoproject.slope.enums.OrderStatus;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.Booking;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.model.User;
import com.lorenzoproject.slope.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(User user, List<Booking> bookings) {
        if(bookings == null || bookings.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one booking");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for(Booking booking : bookings) {
            booking.setOrder(order);
            order.getBookings().add(booking);

            total = total.add(booking.getTotalPrice());
        }
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    @Override
    public void confirmPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        order.setOrderStatus(OrderStatus.PAID);
        order.getBookings()
                .forEach(b -> b.setStatus(BookingStatus.CONFIRMED));
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
