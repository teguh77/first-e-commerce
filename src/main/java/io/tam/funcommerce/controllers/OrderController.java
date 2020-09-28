package io.tam.funcommerce.controllers;

import io.tam.funcommerce.dto.OrderDto;
import io.tam.funcommerce.dto.OrderRequest;
import io.tam.funcommerce.dto.OrderResponse;
import io.tam.funcommerce.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("orders")
    public List<OrderDto> getOrders() {
        return orderService.getProducts();
    }


    @GetMapping("orders/{id}")
    public List<OrderDto> getOrder(@PathVariable("id") Integer id) {
        return orderService.getProduct(id);
    }

    @PostMapping("/orders/new")
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }


    @PostMapping("/orders/payment")
    public PayResponse payment() {
        try {
            Thread.sleep(3000);
            //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        PayResponse payResponse = new PayResponse();
        payResponse.setSuccess(true);
        return payResponse;
    }

}

class PayResponse {
    boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

