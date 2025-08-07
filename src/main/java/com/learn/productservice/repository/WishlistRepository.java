package com.learn.productservice.repository;

import com.learn.productservice.model.Wishlist;
import com.learn.productservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndProductId(User user, Long productId);
}