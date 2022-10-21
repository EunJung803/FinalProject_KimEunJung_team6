package com.ll.exam.mutbooks.app.post.service;

import com.ll.exam.mutbooks.app.base.entity.BaseEntity;
import com.ll.exam.mutbooks.app.member.entity.Member;
import com.ll.exam.mutbooks.app.post.entity.Post;
import com.ll.exam.mutbooks.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public Post write(Long authorId, String subject, String content, String contentHtml) {
        Post post = Post
                .builder()
                .author(new Member(authorId))
                .subject(subject)
                .content(content)
                .contentHtml(contentHtml)
                .build();

        postRepository.save(post);

        return post;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void modify(Post post, String subject, String content) {
        post.setSubject(subject);
        post.setContent(content);

        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<Post> getPostList() {
        return postRepository.findAll();
    }
}
