package com.ll.exam.mutbooks.app.home.controller;

import com.ll.exam.mutbooks.app.post.entity.Post;
import com.ll.exam.mutbooks.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    // TODO : 최신글 100개 노출
    @RequestMapping("/")
    public String main(Model model) {
        return "home/main";
    }
}