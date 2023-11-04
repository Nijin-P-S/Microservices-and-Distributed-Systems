package com.nijin;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
