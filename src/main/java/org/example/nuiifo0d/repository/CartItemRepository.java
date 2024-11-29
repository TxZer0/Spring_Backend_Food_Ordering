package org.example.nuiifo0d.repository;

import org.example.nuiifo0d.entity.Cart;
import org.example.nuiifo0d.entity.CartItem;
import org.example.nuiifo0d.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndItem(Cart cart, Item item);

    Optional<CartItem> findByCartAndItemId(Cart cart, Long itemId);

    void deleteAllByCart(Cart cart);
}
