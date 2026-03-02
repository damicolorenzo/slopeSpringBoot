package com.lorenzoproject.slope.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Subscription {
    private Long id;
    private LocalDateTime subscriptionDate;
    private int duration;
    private BigDecimal price;

    private RegisteredUser user;

}
