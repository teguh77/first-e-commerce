package io.tam.funcommerce.mapper;

import io.tam.funcommerce.dto.OrderDto;
import io.tam.funcommerce.dto.OrderRequest;
import io.tam.funcommerce.dto.ProductRequest;
import io.tam.funcommerce.entities.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", source = "orderDetails.orders.id")
    @Mapping(target = "name", source = "orderDetails.products.title")
    @Mapping(target = "description", source = "orderDetails.products.description")
    @Mapping(target = "price", source = "orderDetails.products.price")
    @Mapping(target = "username", source = "orderDetails.orders.users.username")
    @Mapping(target = "image", source = "orderDetails.products.image")

    OrderDto mapToOrderDto(OrderDetails orderDetails);

}
