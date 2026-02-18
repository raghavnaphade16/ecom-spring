package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader ("X-USER-ID") String userId, @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId,request)){
            return ResponseEntity.badRequest().body("Product out of stock or user not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader ("X-USER-ID") String userId, @PathVariable Long productId){
       boolean result = cartService.deleteItemFromCart(userId,productId);
       return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CartItemResponse>> getAllCart(@RequestHeader ("X-USER-ID") String userId){
        return ResponseEntity.ok(cartService.getAllCart());
    }

    @GetMapping("/getcardbyid")
    public ResponseEntity<List<CartItemResponse>> getCartByUserId(@RequestHeader ("X-USER-ID") String userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

}
