package com.nijin.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
