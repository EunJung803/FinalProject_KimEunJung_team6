package com.ll.exam.final__2022_10_08.app.mybook.service;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.mybook.entity.MyBook;
import com.ll.exam.final__2022_10_08.app.mybook.repository.MyBookRepository;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public void addItems(Member buyer, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            saveItems(buyer, orderItem.getProduct());
        }
    }

    @Transactional
    public void saveItems(Member buyer, Product product) {
        MyBook myBook = MyBook
                .builder()
                .buyer(buyer)
                .product(product)
                .build();

        myBookRepository.save(myBook);
    }
}
