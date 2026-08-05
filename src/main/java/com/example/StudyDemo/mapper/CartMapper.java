package com.example.StudyDemo.mapper;

import com.example.StudyDemo.dto.cart.response.CartItemResponse;
import com.example.StudyDemo.entity.Cart;
import com.example.StudyDemo.entity.CartItem;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {

        Cart findById(@Param("id") Long id);

        int insert(Cart cart);

        CartItem findByCartIdAndProductId(
                        @Param("cartId") Long cartId,
                        @Param("productId") Long productId);

        // int updateQuantity(CartItem cartItem);

        List<CartItemResponse> findItemsByCartId(
                        @Param("cartId") Long cartId);

        CartItem findByIdAndCartId(
                        @Param("itemId") Long itemId,
                        @Param("cartId") Long cartId);

        int updateQuantity(
                        @Param("itemId") Long itemId,
                        @Param("cartId") Long cartId,
                        @Param("quantity") Integer quantity);
}