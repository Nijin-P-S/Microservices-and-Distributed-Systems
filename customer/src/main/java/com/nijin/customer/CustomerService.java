package com.nijin.customer;

import com.nijin.amqp.RabbitMQMessageProducer;
import com.nijin.clients.fraud.FraudCheckResponse;
import com.nijin.clients.fraud.FraudClient;
import com.nijin.clients.notification.NotificationClient;
import com.nijin.clients.notification.NotificationRequest;
import com.nijin.customer.Customer;
import com.nijin.customer.CustomerRegistrationRequest;
import com.nijin.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

//    private final NotificationClient notificationClient;
//    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo: check if the email is valid
        //todo: check if the email not taken

        //store customer in db
        customerRepository.saveAndFlush(customer);

        //todo: check if fraudster
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        //make it async, i.e add it to queue
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome...", customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
                );
    }



    
}
