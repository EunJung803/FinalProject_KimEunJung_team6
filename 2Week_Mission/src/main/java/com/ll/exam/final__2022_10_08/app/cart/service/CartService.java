package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartItemRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;

    public CartItem addItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByBuyerIdAndProductId(buyer.getId(), product.getId()).orElse(null);

        if (oldCartItem != null) {
            return oldCartItem;
        }

        CartItem cartItem = CartItem.builder()
                .buyer(buyer)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    public List<CartItem> getItemsByBuyer(Member buyer) {
        return cartItemRepository.findAllByBuyerId(buyer.getId());
    }
}
