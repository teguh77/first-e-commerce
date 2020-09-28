package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name = "order_id", referencedColumnName = "id", nullable = false)
    private Orders orders;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name = "product_id", referencedColumnName = "id", nullable = false)
    private Products products;

    @Column(nullable = false)
    private Integer quantity;

    public OrderDetails() {
    }

    private OrderDetails(Builder builder) {
        id = builder.id;
        orders = builder.orders;
        products = builder.products;
        quantity = builder.quantity;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public Orders getOrders() {
        return orders;
    }

    public Products getProducts() {
        return products;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public static final class Builder {
        private Integer id;
        private Orders orders;
        private Products products;
        private Integer quantity;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder orders(Orders val) {
            orders = val;
            return this;
        }

        public Builder products(Products val) {
            products = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }
}
