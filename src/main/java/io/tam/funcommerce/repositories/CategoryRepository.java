package io.tam.funcommerce.repositories;

import io.tam.funcommerce.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    Optional<Categories> findByTitle(String title);
}
