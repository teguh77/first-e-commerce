package io.tam.funcommerce.services;

import io.tam.funcommerce.dto.OrderDto;
import io.tam.funcommerce.dto.OrderRequest;
import io.tam.funcommerce.dto.OrderResponse;
import io.tam.funcommerce.entities.OrderDetails;
import io.tam.funcommerce.entities.Orders;
import io.tam.funcommerce.entities.Products;
import io.tam.funcommerce.entities.Users;
import io.tam.funcommerce.exception.ResourceException;
import io.tam.funcommerce.mapper.OrderMapper;
import io.tam.funcommerce.repositories.OrderDetailsRepository;
import io.tam.funcommerce.repositories.OrderRepository;
import io.tam.funcommerce.repositories.ProductRepository;
import io.tam.funcommerce.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailsRepository orderDetailsRepository,
                        OrderMapper orderMapper,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<OrderDto> getProducts() {
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        return orderDetails
                .stream()
                .map(orderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getProduct(Integer id) {
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        return orderDetails
                .stream()
                .filter(od -> od.getOrders().getId().equals(id))
                .map(orderMapper::mapToOrderDto)
                .collect(Collectors.toList());

    }

    public OrderResponse addOrder(OrderRequest orderRequest) {
        if(orderRequest.getUserId() != null && orderRequest.getUserId() > 0) {
            Optional<Users> usersOptional = userRepository.findById(orderRequest.getUserId());
            Users users = usersOptional.orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "No user found"));
            Orders myOrder = Orders.newBuilder()
                    .users(users)
                    .build();
            orderRepository.save(myOrder);
            orderRequest
                    .getProducts()
                    .forEach(p -> {
                        Optional<Products> optionalProducts = productRepository.findById(p.getId());
                        Products products = optionalProducts.orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "No product found"));
                        products.setQuantity(products.getQuantity() > 0 ? products.getQuantity() - p.getIncart() : 0);
                        productRepository.save(products);

                        OrderDetails orderDetails = OrderDetails.newBuilder()
                                .orders(myOrder)
                                .products(products)
                                .quantity(p.getIncart())
                                .build();
                        orderDetailsRepository.save(orderDetails);
                    });

            return OrderResponse.newBuilder()
                    .message("Order successfully placed with order id : " + myOrder.getId())
                    .orderId(myOrder.getId())
                    .success(true)
                    .products(orderRequest.getProducts())
                    .build();

        } else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Cannot insert order");
        }
    }
}
