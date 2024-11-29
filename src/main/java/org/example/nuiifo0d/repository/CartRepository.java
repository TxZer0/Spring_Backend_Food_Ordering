package org.example.nuiifo0d.repository;


import org.example.nuiifo0d.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}
