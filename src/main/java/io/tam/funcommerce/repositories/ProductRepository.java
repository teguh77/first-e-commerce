package io.tam.funcommerce.repositories;

import io.tam.funcommerce.entities.Categories;
import io.tam.funcommerce.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    Optional<Page<Products>> findByCategoriesTitle(String title, Pageable pageable);
}
