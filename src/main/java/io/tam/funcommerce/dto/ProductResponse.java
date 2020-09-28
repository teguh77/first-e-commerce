package io.tam.funcommerce.dto;

import java.util.List;

public class ProductResponse {

    private Integer count;
    private List<ProductDto> products;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
