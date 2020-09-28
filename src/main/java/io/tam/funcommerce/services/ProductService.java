package io.tam.funcommerce.services;

import io.tam.funcommerce.dto.ProductDto;
import io.tam.funcommerce.dto.ProductResponse;
import io.tam.funcommerce.entities.Categories;
import io.tam.funcommerce.entities.Products;
import io.tam.funcommerce.exception.ResourceException;
import io.tam.funcommerce.mapper.ProductMapper;
import io.tam.funcommerce.repositories.CategoryRepository;
import io.tam.funcommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductMapper productMapper,
                          ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse findProducts(Integer pageIndex, Integer pageSize) {
        Optional<Integer> optionalIndex = Optional.ofNullable(pageIndex);
        Optional<Integer> optionalSize = Optional.ofNullable(pageSize);
        Integer page = optionalIndex.orElse(1);
        Integer limit = optionalSize.orElse(10);

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Products> products = productRepository.findAll(pageable);
        List<ProductDto> dtoList = products
                .getContent()
                .stream()
                .map(productMapper::mapToProductDto)
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setCount(products.getNumberOfElements());
        productResponse.setProducts(dtoList);
        return productResponse;
    }

    public ProductDto findProductById(Integer id) {
        Optional<Products> optionalProduct = productRepository.findById(id);
        Products product = optionalProduct.orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "No product found"));

        return productMapper.mapToProductDto(product);
    }

    public ProductResponse findProductByCat(String category, Integer pageIndex, Integer pageSize) {
        Optional<Integer> optionalIndex = Optional.ofNullable(pageIndex);
        Optional<Integer> optionalSize = Optional.ofNullable(pageSize);
        Integer page = optionalIndex.orElse(1);
        Integer limit = optionalSize.orElse(10);
        Pageable pageable = PageRequest.of(page - 1, limit);

        Optional<Page<Products>> byCategories = productRepository.findByCategoriesTitle(category, pageable);
        Page<Products> products = byCategories.orElseThrow(() -> new ResourceException(HttpStatus.BAD_REQUEST, "No product found"));

        List<ProductDto> dtoList = products
                .getContent()
                .stream()
                .map(productMapper::mapToProductDto)
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setCount(products.getNumberOfElements());
        productResponse.setProducts(dtoList);
        return productResponse;
    }
}
