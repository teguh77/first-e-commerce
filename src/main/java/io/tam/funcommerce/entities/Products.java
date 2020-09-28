package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String image;

    @Column(columnDefinition = "text")
    private String images;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private Categories categories;

    public Products() {
    }

    private Products(Builder builder) {
        id = builder.id;
        title = builder.title;
        quantity = builder.quantity;
        description = builder.description;
        price = builder.price;
        image = builder.image;
        images = builder.images;
        categories = builder.categories;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Categories getCategories() {
        return categories;
    }

    public String getImages() {
        return images;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Integer id;
        private String title;
        private Integer quantity;
        private String description;
        private Float price;
        private String image;
        private String images;
        private Categories categories;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder price(Float val) {
            price = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder images(String val) {
            images = val;
            return this;
        }

        public Builder categories(Categories val) {
            categories = val;
            return this;
        }

        public Products build() {
            return new Products(this);
        }
    }
}
