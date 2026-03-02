package com.lorenzoproject.slope.model;

import java.util.HashSet;

public class RegisteredUser {
    private Long id;
    private String username;
    private String password;

    private Cart cart;

    private List<Order> orders;

    private Collection<Role> roles = new HashSet<>();
}
