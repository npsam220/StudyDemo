package com.example.StudyDemo.service;

import com.example.StudyDemo.dto.cart.request.AddCartItemRequest;
import com.example.StudyDemo.dto.cart.response.CartItemResponse;
import com.example.StudyDemo.dto.cart.response.CartResponse;
import com.example.StudyDemo.entity.Cart;
import com.example.StudyDemo.entity.CartItem;
import com.example.StudyDemo.entity.Product;
import com.example.StudyDemo.mapper.CartItemMapper;
import com.example.StudyDemo.mapper.CartMapper;
import com.example.StudyDemo.mapper.ProductMapper;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;

    public CartService(
            CartMapper cartMapper,
            CartItemMapper cartItemMapper,
            ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public void addItem(Long cartId, AddCartItemRequest request) {

        Product product = productMapper.findById(request.getProductId());

        if (product == null) {
            throw new IllegalArgumentException("商品不存在");
        }

        if (product.getStock() == null ||
                request.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("庫存不足");
        }

        Cart cart = cartMapper.findById(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("購物車不存在");
        }

        CartItem existingItem = cartItemMapper.findByCartIdAndProductId(
                cartId,
                request.getProductId());

        if (existingItem == null) {
            CartItem newItem = new CartItem();
            newItem.setCartId(cartId);
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());

            cartItemMapper.insert(newItem);
        } else {
            int newQuantity = existingItem.getQuantity() + request.getQuantity();

            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException("加入後數量超過庫存");
            }

            cartItemMapper.updateQuantity(
                    cartId,
                    request.getProductId(),
                    newQuantity);
        }
    }

    public CartResponse getCart(Long cartId) {

        Cart cart = cartMapper.findById(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("購物車不存在");
        }

        List<CartItemResponse> items = cartItemMapper.findItemsByCartId(cartId);
        // 計算總金額
        int total = items.stream()
                .mapToInt(item -> item.getSubtotal())
                .sum();

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setItems(items);
        response.setTotal(total);

        return response;
    }

    /**
     * 更新購物車商品數量
     *
     * @param cartId    購物車ID
     * @param productId 商品ID
     * @param quantity  新的商品數量
     */
    @Transactional
    public void updateItemQuantity(
            Long cartId,
            Long productId,
            Integer quantity) {
        CartItem cartItem = cartItemMapper.findByCartIdAndProductId(cartId, productId);
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("商品不存在");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("商品數量必須大於 0");
        }

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("購買數量超過庫存");
        }

        Cart cart = cartMapper.findById(cartId);

        if (cart == null) {
            throw new IllegalArgumentException("購物車不存在");
        }

        if (cartItem == null) {
            throw new IllegalArgumentException("購物車商品不存在");
        }

        int updatedRows = cartItemMapper.updateQuantity(
                cartId,
                productId,
                quantity);

        if (updatedRows == 0) {
            throw new IllegalStateException("商品數量更新失敗");
        }
    }
}