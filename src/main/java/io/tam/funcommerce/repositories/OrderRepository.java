package io.tam.funcommerce.repositories;

import io.tam.funcommerce.entities.Orders;
import io.tam.funcommerce.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByUsers(Users users);
    Optional<Orders> findByUsersId(Integer id);
}
