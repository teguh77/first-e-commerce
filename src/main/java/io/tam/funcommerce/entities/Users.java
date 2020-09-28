package io.tam.funcommerce.entities;

import javax.persistence.*;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String username;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String email;
    @Column(columnDefinition = "varchar(255) default 'not set'")
    private String fname;
    @Column(columnDefinition = "varchar(255) default 'not set'")
    private String lname;
    @Column(columnDefinition = "varchar(255) default 18")
    private Integer age;
    @Column(columnDefinition = "varchar(255) default 555")
    private Integer role;
    @Column(columnDefinition = "text")
    private String photoUrl;
    @Column(columnDefinition = "varchar(255) default 'local'", nullable = false)
    private String type;

    public Users() {
    }

    private Users(Builder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        email = builder.email;
        fname = builder.fname;
        lname = builder.lname;
        age = builder.age;
        role = builder.role;
        photoUrl = builder.photoUrl;
        type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getRole() {
        return role;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getType() {
        return type;
    }

    public static final class Builder {
        private Integer id;
        private String username;
        private String password;
        private String email;
        private String fname;
        private String lname;
        private Integer age;
        private Integer role;
        private String photoUrl;
        private String type;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder fname(String val) {
            fname = val;
            return this;
        }

        public Builder lname(String val) {
            lname = val;
            return this;
        }

        public Builder age(Integer val) {
            age = val;
            return this;
        }

        public Builder role(Integer val) {
            role = val;
            return this;
        }

        public Builder photoUrl(String val) {
            photoUrl = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Users build() {
            return new Users(this);
        }
    }
}
