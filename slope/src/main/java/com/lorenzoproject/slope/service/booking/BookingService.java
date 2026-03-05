package com.lorenzoproject.slope.service.booking;

import com.lorenzoproject.slope.enums.BookingStatus;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.*;
import com.lorenzoproject.slope.repository.BookingRepository;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.repository.SubscriptionRepository;
import com.lorenzoproject.slope.repository.UserRepository;
import com.lorenzoproject.slope.request.CreateBookingRequest;
import com.lorenzoproject.slope.request.ParticipantRequest;
import com.lorenzoproject.slope.service.order.OrderService;
import com.lorenzoproject.slope.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
    private final BookingRepository bookingRepository;
    private final SkiFacilityRepository skiFacilityRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final OrderService orderService;
    private final SubscriptionService subscriptionService;

    @Override
    public Order createBooking(CreateBookingRequest request, Long userId) {
        User buyer = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SkiFacility facility = skiFacilityRepository.findById(request.getSkiFacilityId())
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        Booking booking = new Booking();
        booking.setBuyer(buyer);
        booking.setSkiFacility(facility);
        booking.setBookingDate(request.getBookingDate());
        booking.setStatus(BookingStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(ParticipantRequest participantRequest : request.getParticipants()) {
            BookingParticipant participant = new BookingParticipant();
            participant.setBooking(booking);
            participant.setFirstName(participantRequest.getFirstName());
            participant.setLastName(participantRequest.getLastName());
            participant.setBirthData(participantRequest.getBirthDate());

            booking.getParticipants().add(participant);

            BigDecimal participantPrice = facility.getDailyPrice();

            if(participantRequest.isInsurance()) {
                Insurance insurance = new Insurance();
                insurance.setBookingParticipant(participant);
                insurance.setPrice(BigDecimal.valueOf(10));
                insurance.setCoverageType("STANDARD");
                insurance.setStatus("ACTIVE");

                participant.setInsurance(insurance);

                participantPrice = participantPrice.add(insurance.getPrice());
            }

            totalPrice = totalPrice.add(participantPrice);
        }

        totalPrice = subscriptionService.applyDiscountIfAvailable(buyer, totalPrice);
        booking.setTotalPrice(totalPrice);

        return orderService.createOrder(buyer, List.of(booking));
    }
}
