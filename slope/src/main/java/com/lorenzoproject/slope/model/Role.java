package com.lorenzoproject.slope.model;

import java.util.Collection;
import java.util.HashSet;

public class Role {
    private Long id;
    private String name;

    private Collection<RegisteredUser> users = new HashSet<>();
}
