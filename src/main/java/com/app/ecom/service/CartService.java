package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }
        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            //Update
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            //Crete
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        try {
            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isEmpty()) {
                return false;
            }
            Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
            if (userOptional.isEmpty()) {
                return false;
            }

            if (productOpt.isPresent() && userOptional.isPresent()) {
                cartItemRepository.deleteByUserAndProduct(userOptional.get(), productOpt.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }


    public List<CartItemResponse> getAllCart() {
        return cartItemRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    private CartItemResponse mapToUserResponse(CartItem cart) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cart.getId());
        response.setQuantity(cart.getQuantity());
        response.setPrice(cart.getPrice());
        response.setUser(cart.getUser());
        response.setProduct(cart.getProduct());
        response.setCreationDate(cart.getCreationDate());
        response.setUpdatedAt(cart.getUpdatedAt());
        return response;
    }

    public List<CartItemResponse> getCartByUserId(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(user -> cartItemRepository.findByUser(user).stream().map(this::mapToUserResponse).toList()).orElseGet(List::of);
    }

    public void clearCart(String userId) {
      userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);

    }
}
