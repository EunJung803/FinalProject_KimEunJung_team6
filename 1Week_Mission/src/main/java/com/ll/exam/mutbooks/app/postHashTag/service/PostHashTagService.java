package com.ll.exam.mutbooks.app.postHashTag.service;

import com.ll.exam.mutbooks.app.post.entity.Post;
import com.ll.exam.mutbooks.app.postHashTag.entity.PostHashTag;
import com.ll.exam.mutbooks.app.postHashTag.repository.PostHashTagRepository;
import com.ll.exam.mutbooks.app.postKeyword.entity.PostKeyword;
import com.ll.exam.mutbooks.app.postKeyword.service.PostKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHashTagService {
    private final PostKeywordService postKeywordService;
    private final PostHashTagRepository hashTagRepository;

    public void applyPostTags(Post post, String keywordContentsStr) {
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent -> {
            saveHashTag(post, keywordContent);
        });
    }

    private PostHashTag saveHashTag(Post post, String keywordContent) {
        PostKeyword postKeyword = postKeywordService.save(keywordContent);

        Optional<PostHashTag> opHashTag = hashTagRepository.findByPostIdAndPostKeywordId(post.getId(), postKeyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        PostHashTag postHashTag = PostHashTag.builder()
                .post(post)
                .postKeyword(postKeyword)
                .member(post.getAuthor())
                .build();

        hashTagRepository.save(postHashTag);

        return postHashTag;
    }
}