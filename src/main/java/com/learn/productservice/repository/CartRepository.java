package com.learn.productservice.repository;

import com.learn.productservice.model.Cart;
import com.learn.productservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProductId(User user, Long productId);
}