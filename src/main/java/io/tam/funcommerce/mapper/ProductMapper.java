package io.tam.funcommerce.mapper;

import io.tam.funcommerce.dto.ProductDto;
import io.tam.funcommerce.entities.Products;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "name", source = "products.title")
    @Mapping(target = "category", source = "products.categories.title")
    ProductDto mapToProductDto(Products products);
}
