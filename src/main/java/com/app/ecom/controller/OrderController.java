package com.app.ecom.controller;


import com.app.ecom.dto.OrderResponse;
import com.app.ecom.service.OderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader ("X-USER-ID") String userId){
       Optional<OrderResponse> orderResp = orderService.createOrder(userId);
        return orderResp.map(order -> new ResponseEntity<>(order,HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
