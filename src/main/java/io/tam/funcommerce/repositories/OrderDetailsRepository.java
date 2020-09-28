package io.tam.funcommerce.repositories;

import io.tam.funcommerce.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrdersId(Integer id);
}
