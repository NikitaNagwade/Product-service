package com.learn.productservice.repository;

import com.learn.productservice.model.Purchase;
import com.learn.productservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);
}