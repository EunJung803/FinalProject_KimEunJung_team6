package com.ll.exam.mutbooks.app.postKeyword.service;

import com.ll.exam.mutbooks.app.postKeyword.entity.PostKeyword;
import com.ll.exam.mutbooks.app.postKeyword.repository.PostKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostKeywordService {
    private final PostKeywordRepository postKeywordRepository;

    public PostKeyword save(String keywordContent) {
        Optional<PostKeyword> optKeyword = postKeywordRepository.findByContent(keywordContent);

        if ( optKeyword.isPresent() ) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(keywordContent)
                .build();

        postKeywordRepository.save(postKeyword);

        return postKeyword;
    }
}
