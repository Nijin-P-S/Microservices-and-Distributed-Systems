package com.nijin.notification;

import com.nijin.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository  notificationRepository;

    public void send(NotificationRequest notificationRequest){
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId)
                        .toCustomerEmail(notificationRequest.toCustomerEmail)
                        .sender("Nijin")
                        .message(notificationRequest.message)
                        .sendAt(LocalDateTime.now())
                        .build()
        );
    }
}
