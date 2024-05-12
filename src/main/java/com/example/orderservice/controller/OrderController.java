package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/save")
    public String placeNewOrder(@RequestBody Order order){
        log.info("Order Came in the order Controller with order id as: "+order.getOrderId());
        return orderService.placeAnOrder(order);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable String orderId){
        log.info("Get Request for the orderId "+ orderId);
        return orderService.getOrder(orderId);
    }

//    @GetMapping("/getreq")
//    public String getOrderRequestParm(@RequestParam String orderId){
//        log.info("Get Request for request param the orderId "+ orderId);
//        return orderService.getOrder(orderId);
//    }

}
