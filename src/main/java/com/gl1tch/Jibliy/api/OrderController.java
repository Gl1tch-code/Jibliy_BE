package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.OrderCommand;
import com.gl1tch.Jibliy.dto.GiftDTO;
import com.gl1tch.Jibliy.dto.OrderDTO;
import com.gl1tch.Jibliy.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody List<OrderCommand> command, @RequestHeader("Authorization") String authHeader) {
        System.out.println("command received: " + command);
        return ResponseEntity.ok(orderService.createOrder(command, authHeader));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrdersByUser(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(orderService.getAllByUserEmail(authHeader));
    }

    @GetMapping("/delivred-count")
    public ResponseEntity<Integer> getDeliveredCount(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(orderService.getNumberOfDeliveredOrders(authHeader));
    }

    @GetMapping("/earned-gifts")
    public ResponseEntity<List<GiftDTO>> getEarnedGifts(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(orderService.getMyGifts(authHeader));
    }

}
