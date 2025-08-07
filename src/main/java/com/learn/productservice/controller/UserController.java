package com.learn.productservice.controller;

import com.learn.productservice.model.*;
import com.learn.productservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addToCart(@RequestBody Cart cartRequest, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        Optional<Cart> existing = cartRepository.findByUserAndProductId(user, cartRequest.getProduct().getId());
        
        if (existing.isPresent()) {
            Cart cart = existing.get();
            cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
            cartRepository.save(cart);
        } else {
            cartRequest.setUser(user);
            cartRepository.save(cartRequest);
        }
        return ResponseEntity.ok("Added to cart");
    }

    @GetMapping("/cart")
    public List<Cart> getCart(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        return cartRepository.findByUser(user);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return ResponseEntity.ok("Removed from cart");
    }

    @PostMapping("/wishlist")
    public ResponseEntity<String> addToWishlist(@RequestBody Wishlist wishlistRequest, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        Optional<Wishlist> existing = wishlistRepository.findByUserAndProductId(user, wishlistRequest.getProduct().getId());
        
        if (existing.isEmpty()) {
            wishlistRequest.setUser(user);
            wishlistRepository.save(wishlistRequest);
        }
        return ResponseEntity.ok("Added to wishlist");
    }

    @GetMapping("/wishlist")
    public List<Wishlist> getWishlist(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        return wishlistRepository.findByUser(user);
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long id) {
        wishlistRepository.deleteById(id);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseProduct(@RequestBody Purchase purchaseRequest, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        purchaseRequest.setUser(user);
        purchaseRequest.setPurchaseDate(LocalDateTime.now());
        purchaseRepository.save(purchaseRequest);
        return ResponseEntity.ok("Purchase successful");
    }

    @GetMapping("/purchases")
    public List<Purchase> getPurchases(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        return purchaseRepository.findByUser(user);
    }
}