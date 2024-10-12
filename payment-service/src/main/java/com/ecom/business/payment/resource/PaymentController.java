package com.ecom.business.payment.resource;

import com.ecom.business.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String cart = objectMapper.writeValueAsString(request.get("cart"));
            Order response = paymentService.createOrder(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orders/{orderID}/capture")
    public ResponseEntity<Order> captureOrder(@PathVariable String orderID) {
        try {
            Order response = paymentService.captureOrders(orderID);
            return new ResponseEntity<Order>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}