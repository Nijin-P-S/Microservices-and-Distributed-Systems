package com.nijin.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public class NotificationRequest {
    public Integer toCustomerId;
    public String toCustomerEmail;
    public String message;
}
