package com.ll.exam.mutbooks.app.post.controller;

import com.ll.exam.mutbooks.app.post.entity.Post;
import com.ll.exam.mutbooks.app.post.form.dto.PostDto;
import com.ll.exam.mutbooks.app.post.service.PostService;
import com.ll.exam.mutbooks.app.security.dto.MemberContext;
import com.ll.exam.mutbooks.util.Ut;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String showList(Model model) {
        List<Post> postList = postService.getPostList();

        model.addAttribute("postList", postList);

        return "post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostDto postDto) {
        Post post = postService.write(memberContext.getId(), postDto.getSubject(), postDto.getContent(), postDto.getContentHtml(), postDto.getKeywords());

        String msg = "%d번 게시물이 작성되었습니다.".formatted(post.getId());
        msg = Ut.url.encode(msg);
        return "redirect:/post/%d?msg=%s".formatted(post.getId(), msg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "post/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id) {
        Post post = postService.getPostById(id);

        if (memberContext.memberIsNotAuthor(post.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("post", post);

        return "post/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id, @Valid PostDto postDto) {
        Post post = postService.getPostById(id);

        if (memberContext.memberIsNotAuthor(post.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        postService.modify(post, postDto.getSubject(), postDto.getContent());

        String msg = Ut.url.encode("%d번 게시물이 수정되었습니다.".formatted(id));
        return "redirect:/post/%d?msg=%s".formatted(id, msg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id) {
        Post post = postService.getPostById(id);

        if (memberContext.memberIsNotAuthor(post.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        postService.delete(post);

        String msg = Ut.url.encode("%d번 게시물이 삭제되었습니다.".formatted(id));
        return "redirect:/post/list?msg=%s".formatted(msg);
    }
}