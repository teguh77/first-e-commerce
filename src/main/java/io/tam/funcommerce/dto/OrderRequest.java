package io.tam.funcommerce.dto;

import java.util.List;

public class OrderRequest {

    private Integer userId;
    private List<ProductRequest> products;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }
}
