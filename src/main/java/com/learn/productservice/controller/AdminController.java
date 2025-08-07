package com.learn.productservice.controller;

import com.learn.productservice.model.*;
import com.learn.productservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/wishlists")
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    @GetMapping("/purchases")
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}