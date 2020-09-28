package io.tam.funcommerce.controllers;

import io.tam.funcommerce.dto.ProductDto;
import io.tam.funcommerce.services.ProductService;
import io.tam.funcommerce.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public ProductResponse getProducts(@RequestParam(value = "page", required = false) Integer pageIndex,
                                       @RequestParam(value = "limit", required = false) Integer pageSize) {
        return productService.findProducts(pageIndex, pageSize);
    }

    @GetMapping("products/{id}")
    public ProductDto getProductById(@PathVariable("id") Integer id) {
        return productService.findProductById(id);
    }

    @GetMapping("products/category/{category}")
    public ProductResponse getProductByCat(@PathVariable("category") String category,
                                            @RequestParam(value = "page", required = false) Integer pageIndex,
                                            @RequestParam(value = "limit", required = false) Integer pageSize) {
        return productService.findProductByCat(category, pageIndex, pageSize);
    }

}
