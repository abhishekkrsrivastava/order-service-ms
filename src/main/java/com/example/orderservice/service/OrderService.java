package com.example.orderservice.service;

import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.dto.PaymentDTO;
import com.example.orderservice.dto.UserDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;


    @Autowired
    private RestTemplate restTemplate;


    @Value("${order.producer.topic.name}")
    private String topicName;

    private static final String PAYMENT_URL = "http://payment-service-ms-service.payment-service-ms-namespace.svc.cluster.local:9196/payments/";
    private static  final String USER_BASE_URL = "http://user-service-ms/users/";

    public String placeAnOrder(Order order){
        //save copy of an order-service DB
        order.setPurchaseDate(new Date());
        order.setOrderId(UUID.randomUUID().toString());
        orderRepository.save(order);
        // send it to payment service using Kafka
        try {
            kafkaTemplate.send(topicName, new ObjectMapper().writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "Your Order with order id "+ order.getOrderId() +"has been placed ..!!! We will notify once it will confirm";
    }


    public OrderResponseDTO getOrder(String orderId){
      Order order =   orderRepository.findByOrderId(orderId);

      //Rest template to payment
        System.out.println("Sending request to payment dto");



        PaymentDTO paymentDTO = restTemplate.getForObject(PAYMENT_URL+orderId, PaymentDTO.class);

        System.out.println(paymentDTO.getAmount());


        UserDTO userDTO = restTemplate.getForObject(USER_BASE_URL+order.getUserId(), UserDTO.class);

        return OrderResponseDTO.builder()
                .message("All Details")
                .order(order)
                .paymentResponse(paymentDTO)
                .userInfo(userDTO)
                .build();
    }
}
