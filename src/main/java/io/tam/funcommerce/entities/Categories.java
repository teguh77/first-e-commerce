package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String title;

    public Categories() {
    }

    private Categories(Builder builder) {
        id = builder.id;
        title = builder.title;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public static final class Builder {
        private Integer id;
        private String title;

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

        public Categories build() {
            return new Categories(this);
        }
    }
}
