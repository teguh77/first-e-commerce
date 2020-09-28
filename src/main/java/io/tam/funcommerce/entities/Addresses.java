package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(columnDefinition = "varchar(255)")
    private String line1;
    @Column(columnDefinition = "varchar(255)")
    private String line2;

    private String city;
    private String state;
    private String country;
    @Column(columnDefinition = "varchar(15)")
    private String phone;
    private Integer pincode;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

    public Addresses() {
    }

    private Addresses(Builder builder) {
        id = builder.id;
        line1 = builder.line1;
        line2 = builder.line2;
        city = builder.city;
        state = builder.state;
        country = builder.country;
        phone = builder.phone;
        pincode = builder.pincode;
        users = builder.users;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getPincode() {
        return pincode;
    }

    public Users getUsers() {
        return users;
    }

    public static final class Builder {
        private Integer id;
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;
        private String phone;
        private Integer pincode;
        private Users users;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder line1(String val) {
            line1 = val;
            return this;
        }

        public Builder line2(String val) {
            line2 = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder state(String val) {
            state = val;
            return this;
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder pincode(Integer val) {
            pincode = val;
            return this;
        }

        public Builder users(Users val) {
            users = val;
            return this;
        }

        public Addresses build() {
            return new Addresses(this);
        }
    }
}
