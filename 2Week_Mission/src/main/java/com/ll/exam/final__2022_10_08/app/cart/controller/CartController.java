package com.ll.exam.final__2022_10_08.app.cart.controller;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import com.ll.exam.final__2022_10_08.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showItems(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member buyer = memberContext.getMember();
        List<CartItem> items = cartService.getItemsByBuyer(buyer);

        model.addAttribute("items", items);

        return "cart/list";
    }

    @PostMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addItems(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member buyer = memberContext.getMember();
        Product wantedItem = productService.findById(id).get();

        if(cartService.hasItem(wantedItem)){
            return Rq.redirectWithErrorMsg("/product/"+id, "이미 장바구니에 담은 상품입니다.");
        }

        CartItem addItem = cartService.addItem(buyer, wantedItem);

//        model.addAttribute("items", addItem);

        return Rq.redirectWithMsg("/cart/list", "%s이 장바구니에 담겼습니다.".formatted(wantedItem.getSubject()));
    }

    @PostMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public String removeItems(@AuthenticationPrincipal MemberContext memberContext, String ids) {
        Member buyer = memberContext.getMember();

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    CartItem cartItem = cartService.findItemById(id).orElse(null);

                    if (cartService.actorCanDelete(buyer, cartItem)) {
                        cartService.removeItem(cartItem);
                    }
                });

        return Rq.redirectWithMsg("/cart/list", "%d건의 품목을 삭제하였습니다.".formatted(idsArr.length));
    }
}