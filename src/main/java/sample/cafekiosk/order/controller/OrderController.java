package sample.cafekiosk.order.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.common.CustomApiResponse;
import sample.cafekiosk.order.request.OrderCreateRequest;
import sample.cafekiosk.order.response.OrderResponse;
import sample.cafekiosk.order.service.OrderService;

import java.time.LocalDateTime;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerInterface {

    private final OrderService orderService;

    @Override
    @PostMapping("/api/v1/orders/new")
    public CustomApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) throws Exception {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return CustomApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }



}
