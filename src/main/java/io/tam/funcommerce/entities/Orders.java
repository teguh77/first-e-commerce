package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    public Orders() {
    }

    private Orders(Builder builder) {
        id = builder.id;
        users = builder.users;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public Users getUsers() {
        return users;
    }

    public static final class Builder {
        private Integer id;
        private Users users;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder users(Users val) {
            users = val;
            return this;
        }

        public Orders build() {
            return new Orders(this);
        }
    }
}
