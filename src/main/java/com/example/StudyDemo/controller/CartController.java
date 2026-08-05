package com.example.StudyDemo.controller;

import com.example.StudyDemo.dto.cart.request.AddCartItemRequest;
import com.example.StudyDemo.dto.cart.request.UpdateCartItemQuantityRequest;
import com.example.StudyDemo.dto.cart.response.CartResponse;
import com.example.StudyDemo.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<String> addItem(
            @PathVariable Long cartId,
            @Valid @RequestBody AddCartItemRequest request) {
        cartService.addItem(cartId, request);

        return ResponseEntity.ok("商品已加入購物車");
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable Long cartId) {

        CartResponse response = cartService.getCart(cartId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemQuantityRequest request) {

        cartService.updateItemQuantity(
                cartId,
                productId,
                request.getQuantity());

        return ResponseEntity.ok("購物車商品數量已更新");
    }
}