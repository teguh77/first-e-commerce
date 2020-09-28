package io.tam.funcommerce.dto;

import java.util.List;

public class OrderResponse {

    private final String message;
    private final boolean success;
    private final Integer orderId;
    private final List<ProductRequest> products;

    private OrderResponse(Builder builder) {
        message = builder.message;
        success = builder.success;
        orderId = builder.orderId;
        products = builder.products;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public static final class Builder {
        private String message;
        private boolean success;
        private Integer orderId;
        private List<ProductRequest> products;

        private Builder() {
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder success(boolean val) {
            success = val;
            return this;
        }

        public Builder orderId(Integer val) {
            orderId = val;
            return this;
        }

        public Builder products(List<ProductRequest> val) {
            products = val;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(this);
        }
    }
}